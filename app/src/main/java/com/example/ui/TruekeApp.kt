package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.Experience
import com.example.model.Proposal
import com.example.model.UserRole
import com.example.ui.components.BusinessBottomNavigation
import com.example.ui.components.CreatorBottomNavigation
import com.example.ui.components.NotificationSnackbar
import com.example.ui.components.TruekeHeader
import com.example.ui.screens.auth.BusinessOnboardingScreen
import com.example.ui.screens.auth.CreatorOnboardingScreen
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.auth.RoleSelectionScreen
import com.example.ui.screens.auth.WelcomeScreen
import com.example.ui.screens.business.BusinessDashboardScreen
import com.example.ui.screens.business.ContentReviewScreen
import com.example.ui.screens.business.CreateExperienceScreen
import com.example.ui.screens.business.ReviewApplicationsScreen
import com.example.ui.screens.creator.CompleteCollaborationDialog
import com.example.ui.screens.creator.ContentSubmissionScreen
import com.example.ui.screens.creator.CreateProposalScreen
import com.example.ui.screens.creator.CreatorProfileScreen
import com.example.ui.screens.creator.ExperienceDetailScreen
import com.example.ui.screens.creator.ExploreScreen
import com.example.ui.screens.creator.MyCanjesScreen
import com.example.ui.screens.creator.NegotiationDetailDialog
import com.example.ui.screens.creator.QrCheckInScreen
import com.example.viewmodel.AuthStep
import com.example.viewmodel.TruekeViewModel

@Composable
fun TruekeApp(
    viewModel: TruekeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // -------------------------------------------------------------
    // INITIAL AUTHENTICATION & ONBOARDING FLOW
    // -------------------------------------------------------------
    if (!uiState.isAuthenticated) {
        BackHandler(enabled = uiState.authStep != AuthStep.WELCOME) {
            when (uiState.authStep) {
                AuthStep.LOGIN -> viewModel.setAuthStep(AuthStep.WELCOME)
                AuthStep.ROLE_SELECTION -> viewModel.setAuthStep(AuthStep.WELCOME)
                AuthStep.CREATOR_ONBOARDING -> viewModel.setAuthStep(AuthStep.ROLE_SELECTION)
                AuthStep.BUSINESS_ONBOARDING -> viewModel.setAuthStep(AuthStep.ROLE_SELECTION)
                AuthStep.WELCOME -> { /* Top level */ }
            }
        }

        when (uiState.authStep) {
            AuthStep.WELCOME -> {
                WelcomeScreen(
                    onNavigateToLogin = { viewModel.setAuthStep(AuthStep.LOGIN) },
                    onNavigateToRegister = { viewModel.setAuthStep(AuthStep.ROLE_SELECTION) }
                )
            }
            AuthStep.LOGIN -> {
                LoginScreen(
                    onBackClick = { viewModel.setAuthStep(AuthStep.WELCOME) },
                    onLoginSuccess = { email, role ->
                        viewModel.login(email, role)
                    },
                    onNavigateToRegister = { viewModel.setAuthStep(AuthStep.ROLE_SELECTION) }
                )
            }
            AuthStep.ROLE_SELECTION -> {
                RoleSelectionScreen(
                    onBackClick = { viewModel.setAuthStep(AuthStep.WELCOME) },
                    onRoleSelected = { role ->
                        if (role == UserRole.CREATOR) {
                            viewModel.setAuthStep(AuthStep.CREATOR_ONBOARDING)
                        } else {
                            viewModel.setAuthStep(AuthStep.BUSINESS_ONBOARDING)
                        }
                    },
                    onNavigateToLogin = { viewModel.setAuthStep(AuthStep.LOGIN) }
                )
            }
            AuthStep.CREATOR_ONBOARDING -> {
                CreatorOnboardingScreen(
                    onBackClick = { viewModel.setAuthStep(AuthStep.ROLE_SELECTION) },
                    onFinishOnboarding = { name, avatarRes, city, handle, categories ->
                        viewModel.completeCreatorOnboarding(
                            name = name,
                            avatarRes = avatarRes,
                            city = city,
                            handle = handle,
                            categories = categories
                        )
                    }
                )
            }
            AuthStep.BUSINESS_ONBOARDING -> {
                BusinessOnboardingScreen(
                    onBackClick = {
                        if (uiState.isAuthenticated) {
                            viewModel.cancelBusinessCreation()
                        } else {
                            viewModel.setAuthStep(AuthStep.ROLE_SELECTION)
                        }
                    },
                    onFinishOnboarding = { businessName, category, city, logoRes, handleOrWeb ->
                        viewModel.completeBusinessOnboarding(
                            businessName = businessName,
                            category = category,
                            city = city,
                            logoRes = logoRes,
                            handleOrWeb = handleOrWeb
                        )
                    }
                )
            }
        }
        return
    }

    var creatorTab by remember { mutableIntStateOf(0) }
    var businessTab by remember { mutableIntStateOf(0) }

    // Subscreen navigation state
    var selectedExperienceForDetail by remember { mutableStateOf<Experience?>(null) }
    var selectedExperienceForProposal by remember { mutableStateOf<Experience?>(null) }
    var activeProposalForContentSubmission by remember { mutableStateOf<Proposal?>(null) }
    var activeProposalForNegotiationDialog by remember { mutableStateOf<Proposal?>(null) }
    var activeProposalForCompleteDialog by remember { mutableStateOf<Proposal?>(null) }
    var isCreatingExperience by remember { mutableStateOf(false) }

    // Handle Android system back button
    BackHandler(
        enabled = selectedExperienceForDetail != null ||
                selectedExperienceForProposal != null ||
                activeProposalForContentSubmission != null ||
                isCreatingExperience
    ) {
        when {
            selectedExperienceForProposal != null -> selectedExperienceForProposal = null
            activeProposalForContentSubmission != null -> activeProposalForContentSubmission = null
            isCreatingExperience -> isCreatingExperience = false
            selectedExperienceForDetail != null -> selectedExperienceForDetail = null
        }
    }

    val isInsideSubscreen = selectedExperienceForDetail != null ||
            selectedExperienceForProposal != null ||
            activeProposalForContentSubmission != null ||
            isCreatingExperience

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isInsideSubscreen) {
                TruekeHeader(
                    currentRole = uiState.currentRole,
                    onRoleChange = { newRole ->
                        viewModel.switchRole(newRole)
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }
        },
        bottomBar = {
            if (!isInsideSubscreen) {
                if (uiState.currentRole == UserRole.CREATOR) {
                    CreatorBottomNavigation(
                        selectedTab = creatorTab,
                        onTabSelected = { creatorTab = it },
                        proposalsCount = uiState.proposals.size
                    )
                } else {
                    val pendingReviews = uiState.proposals.count {
                        it.status == com.example.model.ProposalStatus.CONTENIDO_ENVIADO ||
                                it.status == com.example.model.ProposalStatus.CORRECCION_SOLICITADA
                    }
                    BusinessBottomNavigation(
                        selectedTab = businessTab,
                        onTabSelected = { businessTab = it },
                        pendingReviewsCount = pendingReviews
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main content depending on active subscreen or current role + tab
            when {
                // 1. Creator Subscreens
                selectedExperienceForProposal != null -> {
                    CreateProposalScreen(
                        experience = selectedExperienceForProposal!!,
                        onBackClick = { selectedExperienceForProposal = null },
                        onSubmitProposal = { creativeIdea, deliverables, preferredDate, companion ->
                            viewModel.submitProposal(
                                experience = selectedExperienceForProposal!!,
                                creativeIdea = creativeIdea,
                                deliverablesSelected = deliverables,
                                preferredDate = preferredDate,
                                companionName = companion
                            )
                            selectedExperienceForProposal = null
                            selectedExperienceForDetail = null
                            creatorTab = 1 // Navigate to "Mis Canjes"
                        }
                    )
                }

                activeProposalForContentSubmission != null -> {
                    ContentSubmissionScreen(
                        proposal = activeProposalForContentSubmission!!,
                        onBackClick = { activeProposalForContentSubmission = null },
                        onSubmitContent = { reel, tiktok, drive, notes ->
                            viewModel.creatorSubmitContent(
                                proposalId = activeProposalForContentSubmission!!.id,
                                reelUrl = reel,
                                tiktokUrl = tiktok,
                                driveUrl = drive,
                                captionNotes = notes
                            )
                            activeProposalForContentSubmission = null
                        }
                    )
                }

                // 2. Business Subscreen: Create new experience
                isCreatingExperience -> {
                    CreateExperienceScreen(
                        onBackClick = { isCreatingExperience = false },
                        onExperienceCreated = { title, catName, catId, city, loc, value, desc, receives, deliv, followers, dates ->
                            viewModel.createExperience(
                                title = title,
                                category = catName,
                                categoryId = catId,
                                city = city,
                                location = loc,
                                estimatedValueCop = value,
                                description = desc,
                                whatCreatorReceives = receives,
                                deliverablesRequested = deliv,
                                minFollowers = followers,
                                datesAvailable = dates
                            )
                            isCreatingExperience = false
                            businessTab = 0
                        }
                    )
                }

                // 3. Shared Experience Detail Screen
                selectedExperienceForDetail != null -> {
                    ExperienceDetailScreen(
                        experience = selectedExperienceForDetail!!,
                        onBackClick = { selectedExperienceForDetail = null },
                        onApplyClick = {
                            selectedExperienceForProposal = selectedExperienceForDetail
                        }
                    )
                }

                // 4. Primary Role Screens
                uiState.currentRole == UserRole.CREATOR -> {
                    when (creatorTab) {
                        0 -> ExploreScreen(
                            experiences = uiState.experiences,
                            selectedCategory = uiState.selectedCategory,
                            selectedCity = uiState.selectedCity,
                            searchQuery = uiState.searchQuery,
                            userName = uiState.currentUserName,
                            onCategorySelected = { viewModel.setCategoryFilter(it) },
                            onCitySelected = { viewModel.setCityFilter(it) },
                            onSearchQueryChange = { viewModel.setSearchQuery(it) },
                            onExperienceClick = { exp -> selectedExperienceForDetail = exp }
                        )

                        1 -> MyCanjesScreen(
                            proposals = uiState.proposals,
                            onOpenProposalNegotiation = { prop ->
                                activeProposalForNegotiationDialog = prop
                            },
                            onOpenQrCheckIn = { prop ->
                                creatorTab = 2 // Switch to QR pass tab
                            },
                            onOpenContentSubmission = { prop ->
                                activeProposalForContentSubmission = prop
                            },
                            onOpenCompleteRegistration = { prop ->
                                activeProposalForCompleteDialog = prop
                            }
                        )

                        2 -> QrCheckInScreen(
                            confirmedProposals = uiState.proposals,
                            onPerformCheckIn = { propId ->
                                viewModel.performQrCheckIn(propId)
                            }
                        )

                        3 -> CreatorProfileScreen(
                            profile = uiState.creatorProfile,
                            userEmail = uiState.authUserEmail,
                            userBusinesses = viewModel.getCurrentUserBusinesses(),
                            onSwitchToBusiness = { viewModel.switchRole(UserRole.BUSINESS) },
                            onRegisterBusiness = { viewModel.requestRegisterBusiness() },
                            onLogout = { viewModel.logout() }
                        )
                    }
                }

                uiState.currentRole == UserRole.BUSINESS -> {
                    when (businessTab) {
                        0 -> BusinessDashboardScreen(
                            businessProfile = uiState.businessProfile,
                            allUserBusinesses = viewModel.getCurrentUserBusinesses(),
                            experiences = uiState.experiences.filter { it.businessName == uiState.businessProfile.name || it.businessName.contains("Lúmina") || it.businessName.contains("El Cielo") || it.businessName.contains("Matiz") },
                            proposals = uiState.proposals,
                            onCreateExperienceClick = { isCreatingExperience = true },
                            onNavigateToApplications = { businessTab = 2 },
                            onNavigateToReviews = { businessTab = 3 },
                            onExperienceClick = { exp -> selectedExperienceForDetail = exp },
                            onSwitchToCreator = { viewModel.switchRole(UserRole.CREATOR) },
                            onSelectBusiness = { bizId -> viewModel.selectActiveBusiness(bizId) },
                            onRegisterAdditionalBusiness = { viewModel.requestRegisterBusiness() },
                            onDeleteBusiness = { bizId -> viewModel.deleteBusiness(bizId) },
                            userEmail = uiState.authUserEmail,
                            onLogout = { viewModel.logout() }
                        )

                        1 -> ExploreScreen(
                            experiences = uiState.experiences,
                            selectedCategory = uiState.selectedCategory,
                            selectedCity = uiState.selectedCity,
                            searchQuery = uiState.searchQuery,
                            userName = uiState.currentUserName,
                            onCategorySelected = { viewModel.setCategoryFilter(it) },
                            onCitySelected = { viewModel.setCityFilter(it) },
                            onSearchQueryChange = { viewModel.setSearchQuery(it) },
                            onExperienceClick = { exp -> selectedExperienceForDetail = exp }
                        )

                        2 -> ReviewApplicationsScreen(
                            proposals = uiState.proposals,
                            onAcceptProposal = { propId ->
                                viewModel.businessAcceptProposal(propId)
                            },
                            onSendCounterProposal = { propId, newDate, msg ->
                                viewModel.businessSendCounterProposal(propId, newDate, msg)
                            },
                            onViewCreatorProfile = { prop ->
                                // Creator evaluation popup or view
                                creatorTab = 3
                                viewModel.switchRole(UserRole.CREATOR)
                            }
                        )

                        3 -> ContentReviewScreen(
                            proposalsWithContent = uiState.proposals,
                            onApproveContent = { propId ->
                                viewModel.businessApproveContent(propId)
                            },
                            onRequestCorrections = { propId, notes ->
                                viewModel.businessRequestCorrections(propId, notes)
                            }
                        )
                    }
                }
            }

            // Notification Snackbar Overlay
            uiState.notificationMessage?.let { msg ->
                NotificationSnackbar(
                    message = msg,
                    onDismiss = { viewModel.dismissNotification() },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            // Dialogs
            activeProposalForNegotiationDialog?.let { prop ->
                NegotiationDetailDialog(
                    proposal = prop,
                    onDismiss = { activeProposalForNegotiationDialog = null },
                    onAcceptCounterProposal = { id ->
                        viewModel.creatorAcceptCounterProposal(id)
                        activeProposalForNegotiationDialog = null
                    }
                )
            }

            activeProposalForCompleteDialog?.let { prop ->
                CompleteCollaborationDialog(
                    proposal = prop,
                    onDismiss = { activeProposalForCompleteDialog = null },
                    onComplete = { rating, creatorRating, review ->
                        viewModel.completeProposalWithRatings(prop.id, rating, creatorRating, review)
                        activeProposalForCompleteDialog = null
                    }
                )
            }
        }
    }
}
