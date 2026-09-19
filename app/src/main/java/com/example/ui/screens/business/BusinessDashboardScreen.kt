package com.example.ui.screens.business

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusinessProfile
import com.example.model.Experience
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.CoralSurface
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldSurface
import com.example.ui.theme.NavyDark
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.PurpleSurface
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.SurfaceWhite
import com.example.viewmodel.TruekeViewModel

import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import com.example.ui.theme.CoralSurface

@Composable
fun BusinessDashboardScreen(
    businessProfile: BusinessProfile,
    allUserBusinesses: List<BusinessProfile> = listOf(businessProfile),
    experiences: List<Experience>,
    proposals: List<Proposal>,
    onCreateExperienceClick: () -> Unit,
    onNavigateToApplications: () -> Unit,
    onNavigateToReviews: () -> Unit,
    onExperienceClick: (Experience) -> Unit,
    onSwitchToCreator: () -> Unit = {},
    onSelectBusiness: (String) -> Unit = {},
    onRegisterAdditionalBusiness: () -> Unit = {},
    onDeleteBusiness: (String) -> Unit = {},
    userEmail: String? = null,
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "¿Quieres cerrar sesión?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = NavyDark
                )
            },
            text = {
                Text(
                    text = "Se cerrará tu sesión actual de negocio en TRUEK-E. Tendrás que volver a ingresar para acceder a tu panel.",
                    fontSize = 14.sp,
                    color = Slate600
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoralAccent,
                        contentColor = SurfaceWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("confirm_business_logout_button")
                ) {
                    Text("Cerrar sesión", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false },
                    modifier = Modifier.testTag("cancel_business_logout_button")
                ) {
                    Text("Cancelar", color = Slate600, fontWeight = FontWeight.Medium)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = SurfaceWhite
        )
    }

    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = {
                Text(
                    text = "¿Eliminar este negocio?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = NavyDark
                )
            },
            text = {
                Text(
                    text = "Se eliminará '${businessProfile.name}' de tu cuenta. Si no tienes otros negocios, volverás automáticamente al modo creador.",
                    fontSize = 14.sp,
                    color = Slate600
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirmDialog = false
                        onDeleteBusiness(businessProfile.id)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoralAccent,
                        contentColor = SurfaceWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("confirm_delete_business_button")
                ) {
                    Text("Eliminar negocio", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteConfirmDialog = false },
                    modifier = Modifier.testTag("cancel_delete_business_button")
                ) {
                    Text("Cancelar", color = Slate600, fontWeight = FontWeight.Medium)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = SurfaceWhite
        )
    }

    val pendingProposals = proposals.filter {
        it.status == ProposalStatus.POSTULADO || it.status == ProposalStatus.EN_NEGOCIACION
    }
    val contentToReview = proposals.filter {
        it.status == ProposalStatus.CONTENIDO_ENVIADO || it.status == ProposalStatus.CORRECCION_SOLICITADA
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Business Header & Welcome
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("business_header_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceWhite),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Storefront,
                                    contentDescription = null,
                                    tint = NavyDark,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = businessProfile.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SurfaceWhite
                                )
                                Text(
                                    text = "${businessProfile.category} • ${businessProfile.city}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate300
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceWhite.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "${businessProfile.rating}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SurfaceWhite
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "¡Hola ${businessProfile.name}! 👋",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = SurfaceWhite
                    )
                    Text(
                        text = "Centro de Operaciones de Canjes y Colaboraciones",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate300
                    )
                }
            }
        }

        // Quick Metrics Dashboard Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Postulaciones pendientes
                BusinessMetricCard(
                    title = "Postulaciones",
                    count = pendingProposals.size.toString(),
                    subtitle = "Nuevas",
                    color = EmeraldDark,
                    bgColor = EmeraldSurface,
                    modifier = Modifier.weight(1f).clickable { onNavigateToApplications() }
                )

                // Contenido por revisar
                BusinessMetricCard(
                    title = "Por Revisar",
                    count = contentToReview.size.toString(),
                    subtitle = "Borradores",
                    color = CoralAccent,
                    bgColor = CoralSurface,
                    modifier = Modifier.weight(1f).clickable { onNavigateToReviews() }
                )

                // Canjes completados
                BusinessMetricCard(
                    title = "Canjes",
                    count = "${businessProfile.completedCanjes}",
                    subtitle = "Completados",
                    color = NavyDark,
                    bgColor = Slate100,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Action Buttons: Create new Canje Experience
        item {
            Button(
                onClick = onCreateExperienceClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("create_experience_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = EmeraldLight,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Publicar Nueva Experiencia de Canje",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Active Notifications / Pending Action items
        if (pendingProposals.isNotEmpty() || contentToReview.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldSurface),
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Acciones pendientes que requieren tu atención:",
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                fontSize = 13.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        if (pendingProposals.isNotEmpty()) {
                            Text(
                                text = "• Tienes ${pendingProposals.size} postulación(es) de creadores para evaluar.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate800
                            )
                        }
                        if (contentToReview.isNotEmpty()) {
                            Text(
                                text = "• Tienes ${contentToReview.size} borrador(es) de contenido enviado para aprobación.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate800
                            )
                        }
                    }
                }
            }
        }

        // Active Experiences list
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tus Experiencias Publicadas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${experiences.size} activas",
                    style = MaterialTheme.typography.labelMedium,
                    color = Slate500
                )
            }
        }

        items(experiences, key = { it.id }) { exp ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExperienceClick(exp) }
                    .testTag("business_exp_card_${exp.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = exp.coverImageRes),
                        contentDescription = exp.title,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = exp.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2
                        )
                        Text(
                            text = "Valor: ${TruekeViewModel.formatCop(exp.estimatedValueCop)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = EmeraldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${exp.applicantsCount} creadores postulados • ${exp.spotsAvailable} cupos",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Slate400
                    )
                }
            }
        }

        // Account & Settings Section for Business
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("business_account_settings_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = NavyDark,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Cuenta de Negocio & Configuración",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Slate50,
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Correo de acceso",
                                        fontSize = 11.sp,
                                        color = Slate500
                                    )
                                    Text(
                                        text = userEmail ?: "negocio@lumina.co",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = NavyDark
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = EmeraldSurface
                                ) {
                                    Text(
                                        text = "Empresa Verificada",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = Slate200, thickness = 0.8.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Negocio activo",
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                                Text(
                                    text = businessProfile.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = NavyDark
                                )
                            }

                            if (allUserBusinesses.size > 1) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(color = Slate200, thickness = 0.8.dp)
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Tus otros negocios (${allUserBusinesses.size})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate600
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    allUserBusinesses.forEach { biz ->
                                        val isCurrent = biz.id == businessProfile.id
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onSelectBusiness(biz.id) },
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isCurrent) NavyDark.copy(alpha = 0.08f) else SurfaceWhite,
                                            border = BorderStroke(1.dp, if (isCurrent) NavyDark else Slate200)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = biz.name,
                                                    fontSize = 12.sp,
                                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (isCurrent) NavyDark else Slate700
                                                )
                                                if (isCurrent) {
                                                    Text("Activo", fontSize = 10.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                                                } else {
                                                    Text("Cambiar", fontSize = 10.sp, color = CoralAccent, fontWeight = FontWeight.SemiBold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Switch back to Creator mode
                    Button(
                        onClick = onSwitchToCreator,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("business_switch_to_creator_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyDark,
                            contentColor = SurfaceWhite
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = SurfaceWhite,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Cambiar a modo Creador",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Register another business
                    OutlinedButton(
                        onClick = onRegisterAdditionalBusiness,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("business_register_another_button"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Slate300),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = NavyDark
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddBusiness,
                                contentDescription = null,
                                tint = NavyDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Registrar otro negocio",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Delete current business
                    OutlinedButton(
                        onClick = { showDeleteConfirmDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .testTag("business_delete_business_button"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, CoralAccent.copy(alpha = 0.4f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = CoralAccent
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = null,
                                tint = CoralAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Eliminar este negocio",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CoralAccent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Logout Action Button
                    Button(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("business_logout_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CoralSurface,
                            contentColor = CoralAccent
                        ),
                        border = BorderStroke(1.dp, CoralAccent.copy(alpha = 0.3f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = "Cerrar sesión",
                                tint = CoralAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Cerrar sesión",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = CoralAccent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessMetricCard(
    title: String,
    count: String,
    subtitle: String,
    color: Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(0.8.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 11.sp, color = Slate600, fontWeight = FontWeight.Medium)
            Text(count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            Text(subtitle, fontSize = 10.sp, color = Slate500)
        }
    }
}
