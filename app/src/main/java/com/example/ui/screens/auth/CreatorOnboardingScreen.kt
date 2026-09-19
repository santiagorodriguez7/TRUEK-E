package com.example.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatorOnboardingScreen(
    onBackClick: () -> Unit,
    onFinishOnboarding: (
        name: String,
        avatarRes: Int,
        city: String,
        handle: String,
        categories: List<String>
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var handle by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("Bogotá") }
    var selectedAvatarRes by remember { mutableStateOf(R.drawable.img_creator_avatar_1787630296671) }
    var selectedCategories by remember {
        mutableStateOf(listOf("Gastronomía", "Hoteles & Glamping", "Cafés de Especialidad"))
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val cities = listOf("Bogotá", "Medellín", "Cartagena", "Cali", "Barranquilla", "Santa Marta", "Pereira")
    val availableCategories = listOf(
        "Gastronomía",
        "Hoteles & Glamping",
        "Cafés de Especialidad",
        "Spas & Wellness",
        "Turismo & Aventura",
        "Lifestyle & Moda"
    )

    val avatarOptions = listOf(
        R.drawable.img_creator_avatar_1787630296671,
        R.drawable.img_restaurant_bogota_1787630225112,
        R.drawable.img_hotel_glamping_1787630242699,
        R.drawable.img_cafe_specialty_1787630260560
    )

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
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("creator_onboarding_back")
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
                        text = "PASO 2 DE 2 • CREADOR",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate600,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title & Subtitle
            Text(
                text = "PERFIL DE CREADOR",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate400,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Cuéntanos sobre ti",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = NavyDark,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "Completa tu información básica para que los negocios puedan validar tu perfil y aprobar tus canjes.",
                fontSize = 13.sp,
                color = Slate500,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            // 1. Photo / Avatar Selection
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, Slate100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Foto de perfil",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Selected Avatar Preview
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(2.dp, EmeraldPrimary, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = selectedAvatarRes),
                            contentDescription = "Avatar seleccionado",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Avatar Selector Row
                    Text(
                        text = "Selecciona una foto o estilo:",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        avatarOptions.forEach { resId ->
                            val isSelected = selectedAvatarRes == resId
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (isSelected) 2.5.dp else 1.dp,
                                        color = if (isSelected) EmeraldPrimary else Slate200,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedAvatarRes = resId }
                            ) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(NavyDark.copy(alpha = 0.35f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = SurfaceWhite,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Personal Information Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, Slate100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Nombre
                    Column {
                        Text(
                            text = "Nombre completo",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                errorMessage = null
                            },
                            placeholder = { Text("Ej. Santiago Mora, Laura Gómez, Carlos...", color = Slate400, fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = Slate500, modifier = Modifier.size(18.dp))
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Slate50,
                                unfocusedContainerColor = Slate50,
                                focusedBorderColor = NavyDark,
                                unfocusedBorderColor = Slate200
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("creator_name_input")
                        )
                    }

                    // Instagram Handle
                    Column {
                        Text(
                            text = "Instagram o red social principal",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = handle,
                            onValueChange = {
                                handle = it
                                errorMessage = null
                            },
                            placeholder = { Text("Ej. @valecharris", color = Slate400, fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Slate500, modifier = Modifier.size(18.dp))
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Slate50,
                                unfocusedContainerColor = Slate50,
                                focusedBorderColor = NavyDark,
                                unfocusedBorderColor = Slate200
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("creator_handle_input")
                        )
                    }

                    // Ciudad
                    Column {
                        Text(
                            text = "Ciudad base",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(cities) { city ->
                                val isSelected = selectedCity == city
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = if (isSelected) NavyDark else Slate50,
                                    border = BorderStroke(1.dp, if (isSelected) NavyDark else Slate200),
                                    modifier = Modifier.clickable { selectedCity = city }
                                ) {
                                    Text(
                                        text = city,
                                        color = if (isSelected) SurfaceWhite else Slate700,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Categories / Interests Selection
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, Slate100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Categorías de contenido / Intereses",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Text(
                        text = "Selecciona los nichos donde creas contenido",
                        fontSize = 11.sp,
                        color = Slate500,
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        availableCategories.forEach { cat ->
                            val isSelected = selectedCategories.contains(cat)
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (isSelected) EmeraldSurface else Slate50,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) EmeraldPrimary else Slate200
                                ),
                                modifier = Modifier.clickable {
                                    selectedCategories = if (isSelected) {
                                        selectedCategories - cat
                                    } else {
                                        selectedCategories + cat
                                    }
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = EmeraldDark,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                    Text(
                                        text = cat,
                                        color = if (isSelected) EmeraldDark else Slate700,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Error message if any
            AnimatedVisibility(visible = errorMessage != null) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFEF2F2),
                    border = BorderStroke(1.dp, Color(0xFFFCA5A5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFDC2626),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Button -> Go to Marketplace
            Button(
                onClick = {
                    if (name.isBlank()) {
                        errorMessage = "Por favor ingresa tu nombre completo."
                    } else if (handle.isBlank()) {
                        errorMessage = "Por favor ingresa tu usuario de Instagram o red principal."
                    } else {
                        onFinishOnboarding(
                            name,
                            selectedAvatarRes,
                            selectedCity,
                            handle,
                            selectedCategories
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("creator_finish_onboarding"),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyDark,
                    contentColor = SurfaceWhite
                )
            ) {
                Text(
                    text = "Comenzar en TRUEK-E",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
