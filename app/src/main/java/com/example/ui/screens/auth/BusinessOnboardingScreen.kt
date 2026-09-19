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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.SurfaceWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BusinessOnboardingScreen(
    onBackClick: () -> Unit,
    onFinishOnboarding: (
        businessName: String,
        category: String,
        city: String,
        logoRes: Int,
        handleOrWeb: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var businessName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Gastronomía & Alta Cocina") }
    var selectedCity by remember { mutableStateOf("Bogotá") }
    var selectedLogoRes by remember { mutableStateOf(R.drawable.img_restaurant_bogota_1787630225112) }
    var handleOrWeb by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val cities = listOf("Bogotá", "Medellín", "Cartagena", "Cali", "Barranquilla", "Santa Marta", "Pereira")
    val businessCategories = listOf(
        "Gastronomía & Alta Cocina",
        "Hoteles & Glamping",
        "Cafés de Especialidad",
        "Spas & Wellness",
        "Turismo & Aventura",
        "Boutique & Retail"
    )

    val logoOptions = listOf(
        R.drawable.img_restaurant_bogota_1787630225112,
        R.drawable.img_hotel_glamping_1787630242699,
        R.drawable.img_cafe_specialty_1787630260560,
        R.drawable.img_spa_wellness_1787630278783
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
                    modifier = Modifier.testTag("business_onboarding_back")
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
                        text = "PASO 2 DE 2 • NEGOCIO",
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
                text = "PERFIL DE NEGOCIO",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate400,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Registra tu Negocio",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = NavyDark,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "Publica experiencias exclusivas y conecta con creadores que elevarán la visibilidad de tu marca.",
                fontSize = 13.sp,
                color = Slate500,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            // Explanatory Banner
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = EmeraldSurface,
                border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.25f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = EmeraldDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Aún no tienes un negocio registrado. Crea tu negocio para empezar a usar Truek-e como negocio.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = EmeraldDark,
                        lineHeight = 16.sp
                    )
                }
            }

            // 1. Logo / Photo Selection
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
                        text = "Foto o logo del negocio",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Selected Logo Preview
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(2.dp, NavyDark, RoundedCornerShape(20.dp))
                    ) {
                        Image(
                            painter = painterResource(id = selectedLogoRes),
                            contentDescription = "Logo seleccionado",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Selecciona una imagen representativa:",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        logoOptions.forEach { resId ->
                            val isSelected = selectedLogoRes == resId
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = if (isSelected) 2.5.dp else 1.dp,
                                        color = if (isSelected) NavyDark else Slate200,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedLogoRes = resId }
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

            // 2. Business Information Card
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
                    // Nombre del negocio
                    Column {
                        Text(
                            text = "Nombre del negocio",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = businessName,
                            onValueChange = {
                                businessName = it
                                errorMessage = null
                            },
                            placeholder = { Text("Ej. Matiz Restaurante", color = Slate400, fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Storefront, contentDescription = null, tint = Slate500, modifier = Modifier.size(18.dp))
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
                                .testTag("business_name_input")
                        )
                    }

                    // Instagram o sitio web
                    Column {
                        Text(
                            text = "Instagram o sitio web",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = handleOrWeb,
                            onValueChange = {
                                handleOrWeb = it
                                errorMessage = null
                            },
                            placeholder = { Text("Ej. @matizrestaurante o www.matiz.co", color = Slate400, fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Language, contentDescription = null, tint = Slate500, modifier = Modifier.size(18.dp))
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
                                .testTag("business_web_input")
                        )
                    }

                    // Ciudad
                    Column {
                        Text(
                            text = "Ciudad",
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

            // 3. Category Selection
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, Slate100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Categoría principal",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Text(
                        text = "Selecciona el sector de tu negocio",
                        fontSize = 11.sp,
                        color = Slate500,
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        businessCategories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (isSelected) NavyDark else Slate50,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) NavyDark else Slate200
                                ),
                                modifier = Modifier.clickable { selectedCategory = cat }
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
                                            tint = SurfaceWhite,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                    Text(
                                        text = cat,
                                        color = if (isSelected) SurfaceWhite else Slate700,
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

            // Action Button -> Go to Business Panel
            Button(
                onClick = {
                    if (businessName.isBlank()) {
                        errorMessage = "Por favor ingresa el nombre de tu negocio."
                    } else if (handleOrWeb.isBlank()) {
                        errorMessage = "Por favor ingresa tu red social o sitio web."
                    } else {
                        onFinishOnboarding(
                            businessName,
                            selectedCategory,
                            selectedCity,
                            selectedLogoRes,
                            handleOrWeb
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("business_finish_onboarding"),
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
