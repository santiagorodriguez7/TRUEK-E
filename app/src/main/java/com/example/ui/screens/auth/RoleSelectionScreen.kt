package com.example.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserRole
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSurface
import com.example.ui.theme.NavyDark
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.SurfaceWhite

@Composable
fun RoleSelectionScreen(
    onBackClick: () -> Unit,
    onRoleSelected: (UserRole) -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRole by remember { mutableStateOf(UserRole.CREATOR) }
    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = SurfaceLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("role_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = NavyDark
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Slate100,
                        border = BorderStroke(1.dp, Slate200)
                    ) {
                        Text(
                            text = "PASO 1 DE 2",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate600,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Title
                Text(
                    text = "¿Cómo quieres usar TRUEK-E?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    letterSpacing = (-0.5).sp
                )

                Text(
                    text = "Selecciona el tipo de cuenta para personalizar tu experiencia de inicio.",
                    fontSize = 13.sp,
                    color = Slate500,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 6.dp, bottom = 24.dp)
                )

                // Role Options Cards
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // 1. Soy Creador Card
                    val isCreator = selectedRole == UserRole.CREATOR
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedRole = UserRole.CREATOR }
                            .testTag("role_select_creator"),
                        shape = RoundedCornerShape(24.dp),
                        color = if (isCreator) SurfaceWhite else Slate50,
                        border = BorderStroke(
                            width = if (isCreator) 2.dp else 1.dp,
                            color = if (isCreator) EmeraldPrimary else Slate200
                        ),
                        shadowElevation = if (isCreator) 3.dp else 0.dp
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(if (isCreator) EmeraldSurface else Slate100),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CameraAlt,
                                            contentDescription = null,
                                            tint = if (isCreator) EmeraldDark else Slate500,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = "Soy creador",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NavyDark
                                        )
                                        Text(
                                            text = "Influencer • UGC • Fotógrafo",
                                            fontSize = 11.sp,
                                            color = Slate400
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isCreator) EmeraldPrimary else Slate200),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isCreator) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Seleccionado",
                                            tint = SurfaceWhite,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Descubre experiencias y crea contenido a cambio de experiencias.",
                                fontSize = 13.sp,
                                color = Slate700,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FeatureTag(text = "Cenas de autor")
                                FeatureTag(text = "Glampings")
                                FeatureTag(text = "Pase QR")
                            }
                        }
                    }

                    // 2. Soy Negocio Card
                    val isBusiness = selectedRole == UserRole.BUSINESS
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedRole = UserRole.BUSINESS }
                            .testTag("role_select_business"),
                        shape = RoundedCornerShape(24.dp),
                        color = if (isBusiness) SurfaceWhite else Slate50,
                        border = BorderStroke(
                            width = if (isBusiness) 2.dp else 1.dp,
                            color = if (isBusiness) NavyDark else Slate200
                        ),
                        shadowElevation = if (isBusiness) 3.dp else 0.dp
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(if (isBusiness) Slate100 else Slate100),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Storefront,
                                            contentDescription = null,
                                            tint = if (isBusiness) NavyDark else Slate500,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = "Soy negocio",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NavyDark
                                        )
                                        Text(
                                            text = "Restaurante • Hotel • Spa",
                                            fontSize = 11.sp,
                                            color = Slate400
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isBusiness) NavyDark else Slate200),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isBusiness) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Seleccionado",
                                            tint = SurfaceWhite,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Publica experiencias y encuentra creadores.",
                                fontSize = 13.sp,
                                color = Slate700,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FeatureTag(text = "Publica Canjes")
                                FeatureTag(text = "Revisa Reels")
                                FeatureTag(text = "Valida QR")
                            }
                        }
                    }
                }
            }

            // Bottom Actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onRoleSelected(selectedRole) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("role_continue_button"),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyDark,
                        contentColor = SurfaceWhite
                    )
                ) {
                    Text(
                        text = if (selectedRole == UserRole.CREATOR) "Continuar como Creador" else "Continuar como Negocio",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta? ",
                        fontSize = 13.sp,
                        color = Slate600
                    )
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark,
                        modifier = Modifier
                            .clickable { onNavigateToLogin() }
                            .testTag("role_goto_login")
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureTag(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Slate100
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Slate600,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
