package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.BusinessProfile
import com.example.model.Category
import com.example.model.CounterProposal
import com.example.model.CreatorProfile
import com.example.model.Experience
import com.example.model.FeedbackNote
import com.example.model.IdealProfile
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.model.SubmittedContent
import com.example.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale

enum class AuthStep {
    WELCOME,
    LOGIN,
    ROLE_SELECTION,
    CREATOR_ONBOARDING,
    BUSINESS_ONBOARDING
}

data class TruekeUiState(
    val isAuthenticated: Boolean = false,
    val authStep: AuthStep = AuthStep.WELCOME,
    val authUserEmail: String? = null,
    val currentRole: UserRole = UserRole.CREATOR,
    val selectedCategory: String = "all",
    val searchQuery: String = "",
    val selectedCity: String = "Todas", // "Todas", "Bogotá", "Medellín", "Cartagena"
    val experiences: List<Experience> = SampleData.initialExperiences,
    val proposals: List<Proposal> = SampleData.initialProposals,
    val selectedExperience: Experience? = null,
    val selectedProposal: Proposal? = null,
    val selectedCreatorForEvaluation: CreatorProfile? = null,
    val creatorProfile: CreatorProfile = SampleData.currentCreator,
    val businessProfile: BusinessProfile = SampleData.currentBusiness,
    val applicantCreators: List<CreatorProfile> = SampleData.sampleApplicantCreators,
    val userNotification: String? = null,
    val notificationMessage: String? = null,
    // Active navigation tab within Creator or Business mode
    val creatorActiveTab: Int = 0, // 0: Descubrir, 1: Mis Canjes, 2: Check-in QR, 3: Mi Perfil
    val businessActiveTab: Int = 0  // 0: Dashboard, 1: Mis Canjes, 2: Postulaciones, 3: Revisión Contenido
) {
    val currentUserName: String
        get() = if (currentRole == UserRole.CREATOR) creatorProfile.name else businessProfile.name

    val greetingFirstName: String
        get() {
            val name = currentUserName.trim()
            if (name.isBlank()) return "Creador"
            val first = name.split("\\s+".toRegex()).firstOrNull() ?: name
            return first.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
}

class TruekeViewModel : ViewModel() {

    // Registered user accounts database
    private val registeredAccounts = mutableMapOf(
        "santiago@trueke.co" to com.example.model.UserAccount(
            email = "santiago@trueke.co",
            name = "Santiago Mora",
            role = UserRole.CREATOR,
            handle = "@santiagomora",
            avatarRes = com.example.R.drawable.img_creator_avatar_1787630296671,
            city = "Bogotá",
            bio = "Creador de contenido foodie y experiencias gastronómicas en Bogotá."
        ),
        "santiago@gmail.com" to com.example.model.UserAccount(
            email = "santiago@gmail.com",
            name = "Santiago Mora",
            role = UserRole.CREATOR,
            handle = "@santiagomora",
            avatarRes = com.example.R.drawable.img_creator_avatar_1787630296671,
            city = "Bogotá",
            bio = "Creador de contenido foodie y experiencias gastronómicas en Bogotá."
        ),
        "laura@trueke.co" to com.example.model.UserAccount(
            email = "laura@trueke.co",
            name = "Laura Gómez",
            role = UserRole.CREATOR,
            handle = "@lauragomez.lifestyle",
            avatarRes = com.example.R.drawable.img_hotel_glamping_1787630242699,
            city = "Medellín",
            bio = "Lifestyle, hotelería y café de especialidad en Medellín."
        ),
        "laura@gmail.com" to com.example.model.UserAccount(
            email = "laura@gmail.com",
            name = "Laura Gómez",
            role = UserRole.CREATOR,
            handle = "@lauragomez.lifestyle",
            avatarRes = com.example.R.drawable.img_hotel_glamping_1787630242699,
            city = "Medellín",
            bio = "Lifestyle, hotelería y café de especialidad en Medellín."
        ),
        "carlos@trueke.co" to com.example.model.UserAccount(
            email = "carlos@trueke.co",
            name = "Carlos Ruiz",
            role = UserRole.CREATOR,
            handle = "@carlosruiz.travel",
            avatarRes = com.example.R.drawable.img_cafe_specialty_1787630260560,
            city = "Cartagena",
            bio = "Fotógrafo de viajes y reseñas culinarias en el Caribe colombiano."
        ),
        "carlos@gmail.com" to com.example.model.UserAccount(
            email = "carlos@gmail.com",
            name = "Carlos Ruiz",
            role = UserRole.CREATOR,
            handle = "@carlosruiz.travel",
            avatarRes = com.example.R.drawable.img_cafe_specialty_1787630260560,
            city = "Cartagena",
            bio = "Fotógrafo de viajes y reseñas culinarias en el Caribe colombiano."
        ),
        "valeria@trueke.co" to com.example.model.UserAccount(
            email = "valeria@trueke.co",
            name = "Valeria Charris",
            role = UserRole.CREATOR,
            handle = "@valecharris.foodie",
            avatarRes = com.example.R.drawable.img_creator_avatar_1787630296671,
            city = "Bogotá",
            bio = "Foodie & Lifestyle creator en Bogotá y Medellín."
        ),
        "matiz@trueke.co" to com.example.model.UserAccount(
            email = "matiz@trueke.co",
            name = "Matiz Cocina Andina",
            role = UserRole.BUSINESS,
            handle = "@matiz.bogota",
            avatarRes = com.example.R.drawable.img_restaurant_bogota_1787630225112,
            city = "Bogotá",
            bio = "Restaurante de autor en la Zona G de Bogotá."
        ),
        "lumina@trueke.co" to com.example.model.UserAccount(
            email = "lumina@trueke.co",
            name = "Lumina Luxury Glamping",
            role = UserRole.BUSINESS,
            handle = "@lumina.glamping",
            avatarRes = com.example.R.drawable.img_hotel_glamping_1787630242699,
            city = "Guatavita",
            bio = "Glamping premium en la laguna de Guatavita."
        )
    )

    // Registered business accounts repository (maps owner email to list of businesses)
    private val registeredBusinesses = mutableMapOf<String, MutableList<BusinessProfile>>(
        "matiz@trueke.co" to mutableListOf(SampleData.currentBusiness.copy(name = "Matiz Cocina Andina", ownerUserId = "matiz@trueke.co")),
        "lumina@trueke.co" to mutableListOf(SampleData.currentBusiness.copy(name = "Lumina Luxury Glamping", ownerUserId = "lumina@trueke.co"))
    )

    private val _uiState = MutableStateFlow(TruekeUiState())
    val uiState: StateFlow<TruekeUiState> = _uiState.asStateFlow()

    fun setAuthStep(step: AuthStep) {
        _uiState.update { it.copy(authStep = step) }
    }

    fun getCurrentUserBusinesses(): List<BusinessProfile> {
        val email = _uiState.value.authUserEmail?.trim()?.lowercase(Locale.getDefault()) ?: return emptyList()
        return registeredBusinesses[email] ?: emptyList()
    }

    fun requestRegisterBusiness() {
        _uiState.update { it.copy(authStep = AuthStep.BUSINESS_ONBOARDING) }
    }

    fun cancelBusinessCreation() {
        _uiState.update { it.copy(authStep = AuthStep.WELCOME, currentRole = UserRole.CREATOR) }
    }

    fun selectActiveBusiness(businessId: String) {
        val businesses = getCurrentUserBusinesses()
        val found = businesses.find { it.id == businessId }
        if (found != null) {
            _uiState.update {
                it.copy(
                    businessProfile = found,
                    currentRole = UserRole.BUSINESS,
                    userNotification = "Cambiando al panel de ${found.name}"
                )
            }
        }
    }

    fun deleteBusiness(businessId: String) {
        val email = _uiState.value.authUserEmail?.trim()?.lowercase(Locale.getDefault()) ?: return
        val list = registeredBusinesses[email] ?: return
        val removed = list.find { it.id == businessId }
        list.removeAll { it.id == businessId }

        if (list.isNotEmpty()) {
            val nextBiz = list.first()
            _uiState.update {
                it.copy(
                    businessProfile = nextBiz,
                    currentRole = UserRole.BUSINESS,
                    userNotification = "Se eliminó '${removed?.name ?: "el negocio"}'. Ahora estás en '${nextBiz.name}'."
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    currentRole = UserRole.CREATOR,
                    userNotification = "Se eliminó '${removed?.name ?: "el negocio"}'. Volviste al modo Creador."
                )
            }
        }
    }

    private fun deriveNameFromEmail(email: String): String {
        val cleanPrefix = email.substringBefore("@")
            .replace(".", " ")
            .replace("_", " ")
            .replace("-", " ")
            .trim()

        val words = cleanPrefix.split("\\s+".toRegex()).filter { it.isNotBlank() }
        if (words.isEmpty()) return "Usuario"

        return words.joinToString(" ") { word ->
            word.lowercase(Locale.getDefault()).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        }
    }

    fun login(email: String, role: UserRole = UserRole.CREATOR) {
        val cleanEmail = email.trim().lowercase(Locale.getDefault())
        val existingAccount = registeredAccounts[cleanEmail]

        val account = if (existingAccount != null) {
            existingAccount
        } else {
            val derivedName = deriveNameFromEmail(cleanEmail)
            val handleName = cleanEmail.substringBefore("@").replace(".", "").replace("-", "").replace("_", "")
            val newAcc = com.example.model.UserAccount(
                email = cleanEmail,
                name = derivedName,
                role = role,
                handle = "@$handleName",
                avatarRes = if (role == UserRole.CREATOR) com.example.R.drawable.img_creator_avatar_1787630296671 else com.example.R.drawable.img_restaurant_bogota_1787630225112,
                city = "Bogotá"
            )
            registeredAccounts[cleanEmail] = newAcc
            newAcc
        }

        val updatedCreator = if (account.role == UserRole.CREATOR) {
            _uiState.value.creatorProfile.copy(
                name = account.name,
                handle = account.handle.ifBlank { "@${account.name.replace(" ", "").lowercase()}" },
                avatarRes = if (account.avatarRes != 0) account.avatarRes else _uiState.value.creatorProfile.avatarRes
            )
        } else {
            _uiState.value.creatorProfile
        }

        val userBizs = registeredBusinesses[cleanEmail] ?: emptyList()
        val updatedBusiness = if (userBizs.isNotEmpty()) {
            userBizs.first()
        } else if (account.role == UserRole.BUSINESS) {
            val biz = _uiState.value.businessProfile.copy(
                name = account.name,
                ownerUserId = cleanEmail,
                handle = account.handle.ifBlank { "@${account.name.replace(" ", "").lowercase()}" },
                logoRes = if (account.avatarRes != 0) account.avatarRes else _uiState.value.businessProfile.logoRes
            )
            registeredBusinesses.getOrPut(cleanEmail) { mutableListOf() }.add(biz)
            biz
        } else {
            _uiState.value.businessProfile
        }

        // Update proposals creator name to match active session creator
        val updatedProposals = _uiState.value.proposals.map { prop ->
            if (account.role == UserRole.CREATOR) {
                prop.copy(
                    creatorName = account.name,
                    creatorHandle = account.handle.ifBlank { "@${account.name.replace(" ", "").lowercase()}" }
                )
            } else {
                prop
            }
        }

        val firstName = account.name.split("\\s+".toRegex()).firstOrNull()?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        } ?: account.name

        _uiState.update {
            it.copy(
                isAuthenticated = true,
                authUserEmail = account.email,
                currentRole = account.role,
                creatorProfile = updatedCreator,
                businessProfile = updatedBusiness,
                proposals = updatedProposals,
                creatorActiveTab = 0,
                businessActiveTab = 0,
                selectedExperience = null,
                selectedProposal = null,
                selectedCreatorForEvaluation = null,
                userNotification = "¡Hola $firstName! Bienvenido a TRUEK-E",
                notificationMessage = "¡Hola $firstName! Bienvenido a TRUEK-E"
            )
        }
    }

    fun completeCreatorOnboarding(
        name: String,
        avatarRes: Int,
        city: String,
        handle: String,
        categories: List<String>
    ) {
        val cleanName = name.trim().ifBlank { "Creador TRUEK-E" }
        val cleanHandle = if (handle.startsWith("@")) handle else "@${handle.ifBlank { cleanName.replace(" ", "").lowercase() }}"
        val userEmail = _uiState.value.authUserEmail ?: "${cleanHandle.replace("@", "").lowercase()}@trueke.co"

        val newAccount = com.example.model.UserAccount(
            email = userEmail,
            name = cleanName,
            role = UserRole.CREATOR,
            handle = cleanHandle,
            avatarRes = avatarRes,
            city = city
        )
        registeredAccounts[userEmail.lowercase()] = newAccount

        val updatedCreator = _uiState.value.creatorProfile.copy(
            name = cleanName,
            handle = cleanHandle,
            location = "$city, Colombia",
            avatarRes = avatarRes,
            categories = if (categories.isNotEmpty()) categories else listOf("Gastronomía", "Lifestyle")
        )

        val updatedProposals = _uiState.value.proposals.map { prop ->
            prop.copy(
                creatorName = cleanName,
                creatorHandle = cleanHandle
            )
        }

        val firstName = cleanName.split("\\s+".toRegex()).firstOrNull() ?: cleanName

        _uiState.update {
            it.copy(
                creatorProfile = updatedCreator,
                proposals = updatedProposals,
                currentRole = UserRole.CREATOR,
                creatorActiveTab = 0,
                isAuthenticated = true,
                authUserEmail = userEmail,
                userNotification = "¡Hola $firstName! Tu perfil ha sido creado exitosamente.",
                notificationMessage = "¡Hola $firstName! Tu perfil ha sido creado exitosamente."
            )
        }
    }

    fun completeBusinessOnboarding(
        businessName: String,
        category: String,
        city: String,
        logoRes: Int,
        handleOrWeb: String
    ) {
        val cleanName = businessName.trim().ifBlank { "Mi Negocio" }
        val cleanHandle = if (handleOrWeb.startsWith("@") || handleOrWeb.contains(".")) handleOrWeb else "@$handleOrWeb"
        val userEmail = _uiState.value.authUserEmail ?: "negocio@${cleanName.replace(" ", "").lowercase()}.co"

        val newAccount = com.example.model.UserAccount(
            email = userEmail,
            name = cleanName,
            role = UserRole.BUSINESS,
            handle = cleanHandle,
            avatarRes = logoRes,
            city = city
        )
        registeredAccounts[userEmail.lowercase()] = newAccount

        val newBiz = BusinessProfile(
            id = "biz_${System.currentTimeMillis()}",
            ownerUserId = userEmail.lowercase(),
            name = cleanName,
            handle = cleanHandle,
            category = category,
            city = city,
            address = "Sede Principal, $city",
            bio = "Perfil oficial de $cleanName en TRUEK-E.",
            rating = 5.0,
            completedCanjes = 0,
            activeCanjes = 1,
            logoRes = logoRes
        )

        val bizList = registeredBusinesses.getOrPut(userEmail.lowercase()) { mutableListOf() }
        bizList.add(newBiz)

        val firstName = cleanName.split("\\s+".toRegex()).firstOrNull() ?: cleanName

        _uiState.update {
            it.copy(
                authStep = AuthStep.WELCOME,
                businessProfile = newBiz,
                currentRole = UserRole.BUSINESS,
                businessActiveTab = 0,
                isAuthenticated = true,
                authUserEmail = userEmail,
                userNotification = "¡Hola $firstName! Tu negocio ha sido registrado exitosamente.",
                notificationMessage = "¡Hola $firstName! Tu negocio ha sido registrado exitosamente."
            )
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                isAuthenticated = false,
                authStep = AuthStep.WELCOME,
                authUserEmail = null,
                selectedExperience = null,
                selectedProposal = null,
                selectedCreatorForEvaluation = null,
                creatorActiveTab = 0,
                businessActiveTab = 0,
                notificationMessage = null,
                userNotification = null
            )
        }
    }

    fun setRole(role: UserRole) {
        if (role == UserRole.BUSINESS) {
            val userBizs = getCurrentUserBusinesses()
            if (userBizs.isEmpty()) {
                // If user has no business, direct to Business creation flow
                _uiState.update {
                    it.copy(
                        authStep = AuthStep.BUSINESS_ONBOARDING,
                        userNotification = "Aún no tienes un negocio registrado. Crea tu negocio para empezar."
                    )
                }
                return
            } else {
                val activeBiz = userBizs.first()
                _uiState.update {
                    it.copy(
                        currentRole = UserRole.BUSINESS,
                        businessProfile = activeBiz,
                        businessActiveTab = 0
                    )
                }
                return
            }
        }
        _uiState.update { it.copy(currentRole = role) }
    }

    fun switchRole(role: UserRole) = setRole(role)

    fun setCreatorActiveTab(tab: Int) {
        _uiState.update { it.copy(creatorActiveTab = tab) }
    }

    fun setBusinessActiveTab(tab: Int) {
        _uiState.update { it.copy(businessActiveTab = tab) }
    }

    fun selectCategory(categoryId: String) {
        _uiState.update { it.copy(selectedCategory = categoryId) }
    }

    fun setCategoryFilter(categoryId: String) = selectCategory(categoryId)

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectCity(city: String) {
        _uiState.update { it.copy(selectedCity = city) }
    }

    fun setCityFilter(city: String) = selectCity(city)

    fun selectExperience(experience: Experience?) {
        _uiState.update { it.copy(selectedExperience = experience) }
    }

    fun selectProposal(proposal: Proposal?) {
        _uiState.update { it.copy(selectedProposal = proposal) }
    }

    fun selectCreatorForEvaluation(creator: CreatorProfile?) {
        _uiState.update { it.copy(selectedCreatorForEvaluation = creator) }
    }

    fun clearNotification() {
        _uiState.update { it.copy(userNotification = null, notificationMessage = null) }
    }

    fun dismissNotification() = clearNotification()

    fun showNotification(message: String) {
        _uiState.update { it.copy(userNotification = message, notificationMessage = message) }
    }

    // Creator: Submit application / proposal
    fun submitProposal(
        experience: Experience,
        creativeIdea: String,
        deliverablesSelected: List<String>,
        preferredDate: String,
        companionName: String?
    ) {
        val newProposal = Proposal(
            id = "prop_${System.currentTimeMillis()}",
            experienceId = experience.id,
            experienceTitle = experience.title,
            businessName = experience.businessName,
            experienceCoverRes = experience.coverImageRes,
            creatorId = _uiState.value.creatorProfile.id,
            creatorName = _uiState.value.creatorProfile.name,
            creatorHandle = _uiState.value.creatorProfile.handle,
            creatorAvatarRes = _uiState.value.creatorProfile.avatarRes,
            creatorFollowersIg = _uiState.value.creatorProfile.igFollowers,
            creatorFollowersTt = _uiState.value.creatorProfile.ttFollowers,
            creatorEngagement = _uiState.value.creatorProfile.engagementRate,
            matchScore = experience.matchPercent,
            creativeIdea = creativeIdea,
            deliverablesSelected = deliverablesSelected,
            preferredDate = preferredDate,
            companionName = companionName,
            status = ProposalStatus.POSTULADO,
            estimatedValueCop = experience.estimatedValueCop,
            location = experience.location,
            city = experience.city,
            feedbackHistory = listOf(
                FeedbackNote(
                    authorName = _uiState.value.creatorProfile.name,
                    authorRole = UserRole.CREATOR,
                    message = "Propuesta enviada: $creativeIdea",
                    timestamp = "Hoy"
                )
            )
        )

        _uiState.update { state ->
            state.copy(
                proposals = listOf(newProposal) + state.proposals,
                userNotification = "¡Propuesta enviada con éxito a ${experience.businessName}!"
            )
        }
    }

    // Creator accepts counter-proposal
    fun acceptCounterProposal(proposalId: String) {
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId && prop.counterProposal != null) {
                    prop.copy(
                        status = ProposalStatus.CONFIRMADO,
                        preferredDate = prop.counterProposal.newDate,
                        deliverablesSelected = prop.counterProposal.newDeliverables,
                        counterProposal = prop.counterProposal.copy(isPending = false),
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = state.creatorProfile.name,
                            authorRole = UserRole.CREATOR,
                            message = "Contrapropuesta aceptada. ¡Nos vemos el ${prop.counterProposal.newDate}!",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "¡Colaboración confirmada! Se ha generado tu pase QR de Check-in.",
                notificationMessage = "¡Colaboración confirmada! Se ha generado tu pase QR de Check-in."
            )
        }
    }

    fun creatorAcceptCounterProposal(proposalId: String) = acceptCounterProposal(proposalId)

    // Business accepts creator proposal directly
    fun businessAcceptProposal(proposalId: String) {
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.CONFIRMADO,
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = prop.businessName,
                            authorRole = UserRole.BUSINESS,
                            message = "¡Propuesta aceptada con gusto! Reserva confirmada para el ${prop.preferredDate}.",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "¡Propuesta aprobada! La colaboración quedó confirmada.",
                notificationMessage = "¡Propuesta aprobada! La colaboración quedó confirmada."
            )
        }
    }

    // Business sends counterproposal
    fun businessSendCounterProposal(
        proposalId: String,
        newDate: String,
        message: String
    ) {
        val existing = _uiState.value.proposals.find { it.id == proposalId }
        businessSendCounterProposal(
            proposalId = proposalId,
            newDate = newDate,
            newDeliverables = existing?.deliverablesSelected ?: emptyList(),
            message = message
        )
    }

    fun businessSendCounterProposal(
        proposalId: String,
        newDate: String,
        newDeliverables: List<String>,
        message: String
    ) {
        val counter = CounterProposal(
            newDate = newDate,
            newDeliverables = newDeliverables,
            message = message,
            isPending = true
        )
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.EN_NEGOCIACION,
                        counterProposal = counter,
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = prop.businessName,
                            authorRole = UserRole.BUSINESS,
                            message = message,
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "Contrapropuesta enviada al creador.",
                notificationMessage = "Contrapropuesta enviada al creador."
            )
        }
    }

    // QR Check-in validation
    fun performQrCheckIn(proposalId: String) {
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.CHECKED_IN,
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = "Sistema TRUEK-E",
                            authorRole = UserRole.BUSINESS,
                            message = "✓ Check-in verificado exitosamente mediante código QR en el establecimiento.",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "¡Check-in validado en el establecimiento con éxito!"
            )
        }
    }

    // Creator submits draft content
    fun submitContent(
        proposalId: String,
        reelUrl: String,
        tiktokUrl: String,
        driveUrl: String,
        captionNotes: String
    ) {
        val content = SubmittedContent(
            reelUrl = reelUrl,
            tiktokUrl = tiktokUrl,
            driveUrl = driveUrl,
            captionNotes = captionNotes,
            submittedAt = "Hoy",
            isApproved = false
        )
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.CONTENIDO_ENVIADO,
                        submittedContent = content,
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = prop.creatorName,
                            authorRole = UserRole.CREATOR,
                            message = "Borradores de contenido subidos para revisión de la marca.",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "¡Contenido enviado para revisión del negocio!",
                notificationMessage = "¡Contenido enviado para revisión del negocio!"
            )
        }
    }

    fun creatorSubmitContent(
        proposalId: String,
        reelUrl: String,
        tiktokUrl: String,
        driveUrl: String,
        captionNotes: String
    ) = submitContent(proposalId, reelUrl, tiktokUrl, driveUrl, captionNotes)

    // Business requests corrections
    fun businessRequestCorrections(proposalId: String, correctionFeedback: String) {
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.CORRECCION_SOLICITADA,
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = prop.businessName,
                            authorRole = UserRole.BUSINESS,
                            message = "Solicitud de ajustes: $correctionFeedback",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "Solicitud de ajustes enviada al creador.",
                notificationMessage = "Solicitud de ajustes enviada al creador."
            )
        }
    }

    // Business approves content
    fun businessApproveContent(proposalId: String) {
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.APROBADO,
                        submittedContent = prop.submittedContent?.copy(isApproved = true),
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = prop.businessName,
                            authorRole = UserRole.BUSINESS,
                            message = "¡Contenido aprobado! Listo para publicar en las redes sociales.",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                userNotification = "¡Contenido aprobado! El creador puede proceder a publicar.",
                notificationMessage = "¡Contenido aprobado! El creador puede proceder a publicar."
            )
        }
    }

    // Complete collaboration and publish rating
    fun completeCollaboration(
        proposalId: String,
        ratingToBusiness: Int,
        ratingToCreator: Int,
        reviewText: String
    ) {
        _uiState.update { state ->
            val updated = state.proposals.map { prop ->
                if (prop.id == proposalId) {
                    prop.copy(
                        status = ProposalStatus.COMPLETADO,
                        ratingGivenToBusiness = ratingToBusiness,
                        ratingGivenToCreator = ratingToCreator,
                        feedbackHistory = prop.feedbackHistory + FeedbackNote(
                            authorName = "TRUEK-E Colaboración",
                            authorRole = state.currentRole,
                            message = "Colaboración completada exitosamente. Reseña: '$reviewText'",
                            timestamp = "Hoy"
                        )
                    )
                } else prop
            }
            state.copy(
                proposals = updated,
                selectedProposal = updated.find { it.id == proposalId },
                creatorProfile = state.creatorProfile.copy(
                    completedCanjes = state.creatorProfile.completedCanjes + 1
                ),
                businessProfile = state.businessProfile.copy(
                    completedCanjes = state.businessProfile.completedCanjes + 1
                ),
                userNotification = "¡Canje TRUEK-E completado con éxito! Gracias por tu calificación.",
                notificationMessage = "¡Canje TRUEK-E completado con éxito! Gracias por tu calificación."
            )
        }
    }

    fun completeProposalWithRatings(
        proposalId: String,
        ratingToBusiness: Int,
        ratingToCreator: Int,
        reviewText: String
    ) = completeCollaboration(proposalId, ratingToBusiness, ratingToCreator, reviewText)

    // Business: Create a new experience
    fun createExperience(
        title: String,
        category: String,
        categoryId: String,
        city: String,
        location: String,
        estimatedValueCop: Long,
        description: String,
        whatCreatorReceives: List<String>,
        deliverablesRequested: List<String>,
        minFollowers: Int,
        datesAvailable: String
    ) {
        val newExp = Experience(
            id = "exp_${System.currentTimeMillis()}",
            title = title,
            businessName = _uiState.value.businessProfile.name,
            businessCategory = category,
            location = location,
            city = city,
            categoryId = categoryId,
            estimatedValueCop = estimatedValueCop,
            matchPercent = 94,
            coverImageRes = SampleData.initialExperiences.first().coverImageRes,
            photos = listOf(SampleData.initialExperiences.first().coverImageRes),
            description = description,
            whatCreatorReceives = whatCreatorReceives,
            deliverablesRequested = deliverablesRequested,
            idealProfile = IdealProfile(
                minFollowers = minFollowers,
                minEngagementRate = 3.5,
                niche = listOf("Lifestyle", "Colombia", category),
                preferredFormats = listOf("Reels", "Stories")
            ),
            datesAvailable = datesAvailable,
            companionAllowed = true,
            spotsAvailable = 4,
            applicantsCount = 0,
            businessRating = 5.0,
            reviewsCount = 1,
            address = location
        )

        _uiState.update { state ->
            state.copy(
                experiences = listOf(newExp) + state.experiences,
                businessProfile = state.businessProfile.copy(
                    activeCanjes = state.businessProfile.activeCanjes + 1
                ),
                userNotification = "¡Nueva experiencia TRUEK-E publicada exitosamente!"
            )
        }
    }

    companion object {
        fun formatCop(amount: Long): String {
            val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
            format.maximumFractionDigits = 0
            return format.format(amount) + " COP"
        }
    }
}
