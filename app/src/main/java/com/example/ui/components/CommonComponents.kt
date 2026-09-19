package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProposalStatus
import com.example.model.UserRole
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.CoralSurface
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldSurface
import com.example.ui.theme.MatchHigh
import com.example.ui.theme.MatchLow
import com.example.ui.theme.MatchMedium
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyMedium
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
import com.example.ui.theme.SurfaceWhite

@Composable
fun TruekeHeader(
    currentRole: UserRole,
    onRoleChange: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, Slate100)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brand Header with clean minimalism tracking
                Column {
                    Text(
                        text = if (currentRole == UserRole.CREATOR) "EXPLORAR" else "PANEL NEGOCIO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate400,
                        letterSpacing = 2.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "TRUEK-E",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = NavyDark,
                            letterSpacing = (-0.5).sp
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = EmeraldSurface
                        ) {
                            Text(
                                text = "🇨🇴 CO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                color = EmeraldDark
                            )
                        }
                    }
                }

                // Role Switcher Toggle (Pill style)
                RoleSwitcher(
                    currentRole = currentRole,
                    onRoleChange = onRoleChange,
                    modifier = Modifier.testTag("role_switcher")
                )
            }
        }
    }
}

@Composable
fun RoleSwitcher(
    currentRole: UserRole,
    onRoleChange: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = Slate100,
        border = BorderStroke(1.dp, Slate200)
    ) {
        Row(
            modifier = Modifier.padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Creator Option
            val isCreator = currentRole == UserRole.CREATOR
            val creatorBg by animateColorAsState(
                targetValue = if (isCreator) NavyDark else Color.Transparent,
                animationSpec = spring(),
                label = "creator_bg"
            )
            val creatorText by animateColorAsState(
                targetValue = if (isCreator) SurfaceWhite else Slate500,
                animationSpec = spring(),
                label = "creator_txt"
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(creatorBg)
                    .clickable { onRoleChange(UserRole.CREATOR) }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Modo Creador",
                    tint = creatorText,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = "Creador",
                    color = creatorText,
                    fontSize = 11.sp,
                    fontWeight = if (isCreator) FontWeight.Bold else FontWeight.Medium
                )
            }

            // Business Option
            val isBusiness = currentRole == UserRole.BUSINESS
            val businessBg by animateColorAsState(
                targetValue = if (isBusiness) NavyDark else Color.Transparent,
                animationSpec = spring(),
                label = "biz_bg"
            )
            val businessText by animateColorAsState(
                targetValue = if (isBusiness) SurfaceWhite else Slate500,
                animationSpec = spring(),
                label = "biz_txt"
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(businessBg)
                    .clickable { onRoleChange(UserRole.BUSINESS) }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Storefront,
                    contentDescription = "Modo Negocio",
                    tint = businessText,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = "Negocio",
                    color = businessText,
                    fontSize = 11.sp,
                    fontWeight = if (isBusiness) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun MatchBadge(
    matchPercent: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = SurfaceWhite.copy(alpha = 0.95f),
        border = BorderStroke(1.dp, Slate100),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(EmeraldPrimary)
            )
            Text(
                text = "$matchPercent% MATCH",
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = Slate800,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun CommercialValueBadge(
    amountFormatted: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = NavyDark
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Handshake,
                contentDescription = "Canje comercial",
                tint = EmeraldLight,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = amountFormatted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SurfaceWhite
            )
        }
    }
}

@Composable
fun ProposalStatusChip(
    status: ProposalStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, icon) = when (status) {
        ProposalStatus.POSTULADO -> Triple(Slate100, Slate700, Icons.Default.Campaign)
        ProposalStatus.EN_NEGOCIACION -> Triple(GoldSurface, GoldAccent, Icons.Default.SwapHoriz)
        ProposalStatus.CONFIRMADO -> Triple(EmeraldSurface, EmeraldDark, Icons.Default.CheckCircle)
        ProposalStatus.CHECKED_IN -> Triple(PurpleSurface, PurpleAccent, Icons.Default.QrCodeScanner)
        ProposalStatus.CONTENIDO_ENVIADO -> Triple(Slate100, Slate800, Icons.Default.RateReview)
        ProposalStatus.CORRECCION_SOLICITADA -> Triple(CoralSurface, CoralAccent, Icons.Default.RateReview)
        ProposalStatus.APROBADO -> Triple(EmeraldSurface, EmeraldPrimary, Icons.Default.Check)
        ProposalStatus.COMPLETADO -> Triple(NavyDark, SurfaceWhite, Icons.Default.Verified)
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = bgColor,
        border = BorderStroke(1.dp, if (status == ProposalStatus.COMPLETADO) NavyDark else Slate200)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = status.labelEs,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = status.labelEs,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun CreatorBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    proposalsCount: Int = 0,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, Slate100)
    ) {
        NavigationBar(
            containerColor = SurfaceWhite,
            contentColor = NavyDark,
            tonalElevation = 0.dp,
            modifier = Modifier.navigationBarsPadding()
        ) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 0) Icons.Filled.Explore else Icons.Outlined.Explore,
                        contentDescription = "Explorar"
                    )
                },
                label = {
                    Text(
                        "EXPLORAR",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )

            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (proposalsCount > 0) {
                                Badge(containerColor = EmeraldPrimary, contentColor = SurfaceWhite) {
                                    Text(proposalsCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedTab == 1) Icons.Filled.Handshake else Icons.Outlined.Handshake,
                            contentDescription = "Mis Canjes"
                        )
                    }
                },
                label = {
                    Text(
                        "MIS CANJES",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )

            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = { onTabSelected(2) },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 2) Icons.Filled.QrCodeScanner else Icons.Outlined.QrCodeScanner,
                        contentDescription = "Pase QR"
                    )
                },
                label = {
                    Text(
                        "PASE QR",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )

            NavigationBarItem(
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 3) Icons.Filled.Person else Icons.Outlined.Person,
                        contentDescription = "Mi Perfil"
                    )
                },
                label = {
                    Text(
                        "PERFIL",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )
        }
    }
}

@Composable
fun BusinessBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    pendingReviewsCount: Int = 0,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, Slate100)
    ) {
        NavigationBar(
            containerColor = SurfaceWhite,
            contentColor = NavyDark,
            tonalElevation = 0.dp,
            modifier = Modifier.navigationBarsPadding()
        ) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                icon = { Icon(Icons.Filled.Dashboard, contentDescription = "Panel") },
                label = {
                    Text(
                        "PANEL",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )

            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                icon = { Icon(Icons.Filled.Storefront, contentDescription = "Mis Canjes") },
                label = {
                    Text(
                        "CANJES",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )

            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = { onTabSelected(2) },
                icon = { Icon(Icons.Filled.Campaign, contentDescription = "Postulaciones") },
                label = {
                    Text(
                        "SOLICITUDES",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )

            NavigationBarItem(
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (pendingReviewsCount > 0) {
                                Badge(containerColor = CoralAccent, contentColor = SurfaceWhite) {
                                    Text(pendingReviewsCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Filled.RateReview, contentDescription = "Revisión")
                    }
                },
                label = {
                    Text(
                        "REVISIÓN",
                        fontSize = 9.sp,
                        fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyDark,
                    indicatorColor = Slate100,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400
                )
            )
        }
    }
}

@Composable
fun NotificationSnackbar(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        color = NavyDark,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = EmeraldLight,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SurfaceWhite
                )
            }
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = EmeraldLight,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onDismiss() }
                    .padding(4.dp)
            )
        }
    }
}
