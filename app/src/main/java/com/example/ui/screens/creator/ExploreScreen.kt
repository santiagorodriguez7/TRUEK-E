package com.example.ui.screens.creator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.Experience
import com.example.ui.components.CommercialValueBadge
import com.example.ui.components.MatchBadge
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSurface
import com.example.ui.theme.GoldAccent
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
import com.example.viewmodel.TruekeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    experiences: List<Experience>,
    selectedCategory: String,
    selectedCity: String,
    searchQuery: String,
    userName: String = "Creador",
    onCategorySelected: (String) -> Unit,
    onCitySelected: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onExperienceClick: (Experience) -> Unit,
    modifier: Modifier = Modifier
) {
    val cities = listOf("Todas", "Bogotá", "Medellín", "Cartagena")

    val filteredExperiences = experiences.filter { exp ->
        val matchesCategory = selectedCategory == "all" || exp.categoryId == selectedCategory
        val matchesCity = selectedCity == "Todas" || exp.city.equals(selectedCity, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                exp.title.contains(searchQuery, ignoreCase = true) ||
                exp.businessName.contains(searchQuery, ignoreCase = true) ||
                exp.location.contains(searchQuery, ignoreCase = true) ||
                exp.businessCategory.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesCity && matchesSearch
    }

    val displayName = userName.trim().split("\\s+".toRegex()).firstOrNull()?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(java.util.Locale.getDefault()) else it.toString()
    }?.ifBlank { "Creador" } ?: "Creador"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Dynamic User Greeting Header
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, Slate100)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hola $displayName",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = NavyDark,
                                letterSpacing = (-0.5).sp,
                                modifier = Modifier.testTag("explore_greeting_text")
                            )
                            Text(
                                text = "Explora canjes exclusivos disponibles para ti",
                                fontSize = 12.sp,
                                color = Slate500,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = EmeraldSurface,
                            border = BorderStroke(1.dp, EmeraldLight)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = EmeraldDark,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Match Activo",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search Bar & City Selector
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWhite)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("explore_search_field"),
                    placeholder = {
                        Text(
                            "Buscar restaurantes, glampings, spas...",
                            fontSize = 13.sp,
                            color = Slate400
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = NavyDark,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar", tint = Slate500, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Slate50,
                        unfocusedContainerColor = Slate50,
                        focusedBorderColor = NavyDark,
                        unfocusedBorderColor = Slate200
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Cities Row (Minimalist Pills)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    cities.forEach { city ->
                        val isSelected = selectedCity == city
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isSelected) NavyDark else Slate50,
                            border = BorderStroke(1.dp, if (isSelected) NavyDark else Slate200),
                            modifier = Modifier.clickable { onCitySelected(city) }
                        ) {
                            Text(
                                text = city,
                                color = if (isSelected) SurfaceWhite else Slate600,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Category Pills Navigation Bar
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceWhite,
                border = BorderStroke(1.dp, Slate100)
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(SampleData.categories) { cat ->
                        val isSelected = selectedCategory == cat.id
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isSelected) NavyDark else Slate50,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) NavyDark else Slate200
                            ),
                            modifier = Modifier.clickable { onCategorySelected(cat.id) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = cat.name,
                                    color = if (isSelected) SurfaceWhite else Slate600,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                                if (cat.count > 0) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isSelected) EmeraldPrimary else Slate200
                                    ) {
                                        Text(
                                            text = cat.count.toString(),
                                            color = if (isSelected) SurfaceWhite else Slate600,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section Title & Result Count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedCity == "Todas") "DESTACADOS EN COLOMBIA" else "DESTACADOS EN ${selectedCity.uppercase()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate800,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${filteredExperiences.size} canjes",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate400
                )
            }
        }

        // Experience Cards List
        if (filteredExperiences.isEmpty()) {
            item {
                EmptyExperiencesView()
            }
        } else {
            items(filteredExperiences, key = { it.id }) { exp ->
                ExperienceCard(
                    experience = exp,
                    onClick = { onExperienceClick(exp) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ExperienceCard(
    experience: Experience,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("experience_card_${experience.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, Slate100)
    ) {
        Column {
            // Photo Container with Clean Minimalist Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Image(
                    painter = painterResource(id = experience.coverImageRes),
                    contentDescription = experience.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Top badges: Match % and Location pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MatchBadge(matchPercent = experience.matchPercent)

                    Surface(
                        shape = CircleShape,
                        color = NavyDark,
                        shadowElevation = 2.dp
                    ) {
                        Box(
                            modifier = Modifier.size(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Handshake,
                                contentDescription = "Canje",
                                tint = SurfaceWhite,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Card Body Information
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                // Micro location uppercase tag
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Slate400,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "${experience.location.uppercase()}, ${experience.city.uppercase()}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate400,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Title
                Text(
                    text = experience.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark,
                    lineHeight = 22.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Description
                Text(
                    text = experience.description,
                    fontSize = 12.sp,
                    color = Slate500,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Bottom Action & Value Row
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Transparent,
                    border = BorderStroke(0.dp, Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "VALOR ESTIMADO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate400,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = TruekeViewModel.formatCop(experience.estimatedValueCop),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = Slate800
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = NavyDark,
                            modifier = Modifier.clickable { onClick() }
                        ) {
                            Text(
                                text = "Ver Detalles",
                                color = SurfaceWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 9.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyExperiencesView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Slate400,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "No encontramos experiencias con esos filtros",
            style = MaterialTheme.typography.titleMedium,
            color = NavyDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Prueba cambiando de ciudad o seleccionando la categoría 'Todos'.",
            style = MaterialTheme.typography.bodySmall,
            color = Slate500
        )
    }
}
