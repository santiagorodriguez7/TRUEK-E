package com.example.ui.screens.business

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSurface
import com.example.ui.theme.NavyDark
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExperienceScreen(
    onBackClick: () -> Unit,
    onExperienceCreated: (
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
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("Tarde de Coctelería de Autor & Tapas para 2") }
    var selectedCategoryId by remember { mutableStateOf("gastro") }
    var selectedCategoryName by remember { mutableStateOf("Gastronomía de Autor") }
    var city by remember { mutableStateOf("Bogotá") }
    var location by remember { mutableStateOf("Parque de la 93, Bogotá") }
    var estimatedValueText by remember { mutableStateOf("380000") }
    var description by remember {
        mutableStateOf("Degustación de 4 cócteles de autor inspirados en la botánica colombiana acompañados de 3 tablas de tapas gourmet en nuestra terraza VIP.")
    }
    var datesAvailable by remember { mutableStateOf("Martes a Viernes (5:00 PM a 9:00 PM)") }
    var minFollowersText by remember { mutableStateOf("20000") }

    val receivesItems = remember {
        mutableStateListOf(
            "Cata de 4 cócteles de autor para 2 personas",
            "Tabla de tapas gourmet a elección",
            "Mesa VIP con mejor vista en la terraza"
        )
    }
    var newReceiveInput by remember { mutableStateOf("") }

    val deliverablesList = remember {
        mutableStateListOf(
            "1 Reel dinámico (30-45s) destacando la coctelería y el ambiente",
            "3 Historias de Instagram en vivo con mención y sticker de ubicación",
            "3 Fotos HD en formato vertical para redes sociales de la marca"
        )
    }

    val categories = listOf(
        "gastro" to "Gastronomía de Autor",
        "hotel" to "Hoteles & Glamping",
        "cafe" to "Cafés de Especialidad",
        "spa" to "Spas & Wellness",
        "turismo" to "Turismo & Aventura"
    )

    val cities = listOf("Bogotá", "Medellín", "Cartagena", "Santa Marta", "Cali")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Publicar Experiencia",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("create_exp_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = NavyDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceWhite,
                    titleContentColor = NavyDark
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceWhite,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            val value = estimatedValueText.toLongOrNull() ?: 350000L
                            val followers = minFollowersText.toIntOrNull() ?: 20000
                            onExperienceCreated(
                                title,
                                selectedCategoryName,
                                selectedCategoryId,
                                city,
                                location,
                                value,
                                description,
                                receivesItems.toList(),
                                deliverablesList.toList(),
                                followers,
                                datesAvailable
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("publish_experience_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                        enabled = title.isNotBlank() && description.isNotBlank() && location.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = EmeraldLight,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Publicar en el Marketplace TRUEK-E",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceLight)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Section 1: Basic Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "1. Información de la Experiencia",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        // Title
                        Column {
                            Text("Título de la experiencia:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                modifier = Modifier.fillMaxWidth().testTag("new_exp_title_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Slate50,
                                    unfocusedContainerColor = Slate50,
                                    focusedBorderColor = NavyDark,
                                    unfocusedBorderColor = Slate200
                                )
                            )
                        }

                        // Category Chips
                        Column {
                            Text("Categoría:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(categories) { (id, name) ->
                                    val isSelected = selectedCategoryId == id
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) NavyDark else Slate100,
                                        modifier = Modifier.clickable {
                                            selectedCategoryId = id
                                            selectedCategoryName = name
                                        }
                                    ) {
                                        Text(
                                            text = name,
                                            fontSize = 11.sp,
                                            color = if (isSelected) SurfaceWhite else Slate700,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // City Chips
                        Column {
                            Text("Ciudad en Colombia:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(cities) { c ->
                                    val isSelected = city == c
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) EmeraldPrimary else Slate100,
                                        modifier = Modifier.clickable { city = c }
                                    ) {
                                        Text(
                                            text = c,
                                            fontSize = 11.sp,
                                            color = if (isSelected) SurfaceWhite else Slate700,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Location / Address
                        Column {
                            Text("Zona / Dirección:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = location,
                                onValueChange = { location = it },
                                modifier = Modifier.fillMaxWidth().testTag("new_exp_location_input"),
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate500) },
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Slate50,
                                    unfocusedContainerColor = Slate50,
                                    focusedBorderColor = NavyDark,
                                    unfocusedBorderColor = Slate200
                                )
                            )
                        }

                        // Commercial Value in COP
                        Column {
                            Text("Valor comercial estimado (COP $):", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = estimatedValueText,
                                onValueChange = { estimatedValueText = it },
                                modifier = Modifier.fillMaxWidth().testTag("new_exp_value_input"),
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null, tint = EmeraldDark) },
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Slate50,
                                    unfocusedContainerColor = Slate50,
                                    focusedBorderColor = NavyDark,
                                    unfocusedBorderColor = Slate200
                                )
                            )
                        }

                        // Description
                        Column {
                            Text("Descripción detallada:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                modifier = Modifier.fillMaxWidth().height(90.dp).testTag("new_exp_desc_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Slate50,
                                    unfocusedContainerColor = Slate50,
                                    focusedBorderColor = NavyDark,
                                    unfocusedBorderColor = Slate200
                                )
                            )
                        }
                    }
                }
            }

            // Section 2: What Creator Receives (Lo que recibe)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Handshake, contentDescription = null, tint = EmeraldDark)
                            Text(
                                text = "2. Lo que el creador recibe",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        receivesItems.forEachIndexed { idx, item ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                    Text(item, style = MaterialTheme.typography.bodySmall, color = Slate800)
                                }
                                IconButton(
                                    onClick = { if (receivesItems.size > 1) receivesItems.removeAt(idx) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Eliminar", tint = Slate400, modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = newReceiveInput,
                                onValueChange = { newReceiveInput = it },
                                modifier = Modifier.weight(1f),
                                placeholder = { Text("Agregar ítem de canje...", fontSize = 12.sp) },
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Slate50,
                                    unfocusedContainerColor = Slate50
                                )
                            )
                            Button(
                                onClick = {
                                    if (newReceiveInput.isNotBlank()) {
                                        receivesItems.add(newReceiveInput)
                                        newReceiveInput = ""
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                            ) {
                                Text("+")
                            }
                        }
                    }
                }
            }

            // Section 3: Deliverables & Creator Requirements
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Videocam, contentDescription = null, tint = NavyDark)
                            Text(
                                text = "3. Entregables & Requisitos del Creador",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text("Entregables solicitados:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                        deliverablesList.forEach { del ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NavyDark, modifier = Modifier.size(14.dp))
                                Text(del, style = MaterialTheme.typography.bodySmall, color = Slate700)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("Seguidores mínimos sugeridos:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                        OutlinedTextField(
                            value = minFollowersText,
                            onValueChange = { minFollowersText = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Ej: 20000") },
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Slate50,
                                unfocusedContainerColor = Slate50
                            )
                        )

                        Text("Fechas disponibles:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                        OutlinedTextField(
                            value = datesAvailable,
                            onValueChange = { datesAvailable = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Ej: Jueves a Sábados") },
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Slate50,
                                unfocusedContainerColor = Slate50
                            )
                        )
                    }
                }
            }
        }
    }
}
