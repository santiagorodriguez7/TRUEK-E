package com.example.ui.screens.creator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.ui.components.ProposalStatusChip
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.CoralSurface
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
fun ContentSubmissionScreen(
    proposal: Proposal,
    onBackClick: () -> Unit,
    onSubmitContent: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var reelUrl by remember {
        mutableStateOf(proposal.submittedContent?.reelUrl ?: "https://instagram.com/reel/C8k9xL2pQ1A_draft")
    }
    var tiktokUrl by remember {
        mutableStateOf(proposal.submittedContent?.tiktokUrl ?: "https://tiktok.com/@valecharris/video/73918239102")
    }
    var driveUrl by remember {
        mutableStateOf(proposal.submittedContent?.driveUrl ?: "https://drive.google.com/drive/folders/trueke-hd-assets-colombia")
    }
    var captionNotes by remember {
        mutableStateOf(
            proposal.submittedContent?.captionNotes
                ?: "¡Un plan imperdible en Colombia! 🇨🇴✨ Visitamos @${proposal.businessName.lowercase().replace(" ", "")} y fue una experiencia de otro nivel. ¿Con quién irías? Guarda este video y etiqueta a tu acompañante favorito! #TruekeColombia #PlanesColombia #FoodiesColombia"
        )
    }

    val isCorrectionRequested = proposal.status == ProposalStatus.CORRECCION_SOLICITADA

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isCorrectionRequested) "Ajustar y Reenviar Contenido" else "Entrega de Contenido",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("content_submission_back_button")
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
                            if (reelUrl.isNotBlank() || driveUrl.isNotBlank()) {
                                onSubmitContent(reelUrl, tiktokUrl, driveUrl, captionNotes)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("send_content_to_business_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                        enabled = reelUrl.isNotBlank() || driveUrl.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = EmeraldLight,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCorrectionRequested) "Reenviar Contenido Ajustado" else "Enviar a Aprobación de la Marca",
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
            // Experience Header Snippet
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                            painter = painterResource(id = proposal.experienceCoverRes),
                            contentDescription = proposal.experienceTitle,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(proposal.businessName, style = MaterialTheme.typography.labelSmall, color = Slate500)
                            Text(proposal.experienceTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 2)
                            ProposalStatusChip(status = proposal.status)
                        }
                    }
                }
            }

            // Corrections Banner if requested
            if (isCorrectionRequested) {
                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = CoralSurface,
                        border = BorderStroke(1.dp, CoralAccent.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Feedback, contentDescription = null, tint = CoralAccent, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "Ajustes solicitados por ${proposal.businessName}:",
                                    fontWeight = FontWeight.Bold,
                                    color = CoralAccent,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            val lastFeedback = proposal.feedbackHistory.lastOrNull { it.authorRole == com.example.model.UserRole.BUSINESS }?.message
                                ?: "Por favor revisa que se mencione el plato estrella y se use el sticker de ubicación."
                            Text(
                                text = lastFeedback,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Slate800,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Deliverables Checklist Reminder
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Entregables acordados para este canje:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        proposal.deliverablesSelected.forEach { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                Text(item, style = MaterialTheme.typography.bodySmall, color = Slate700)
                            }
                        }
                    }
                }
            }

            // Input Fields: Reel, TikTok, Drive
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Enlaces de los borradores / archivos",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )

                        // Reel Link
                        Column {
                            Text("1. Enlace borrador Instagram Reel:", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = reelUrl,
                                onValueChange = { reelUrl = it },
                                modifier = Modifier.fillMaxWidth().testTag("reel_url_input"),
                                leadingIcon = { Icon(Icons.Default.Videocam, contentDescription = null, tint = Slate500) },
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

                        // TikTok Link
                        Column {
                            Text("2. Enlace borrador TikTok (opcional):", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = tiktokUrl,
                                onValueChange = { tiktokUrl = it },
                                modifier = Modifier.fillMaxWidth().testTag("tiktok_url_input"),
                                leadingIcon = { Icon(Icons.Default.Link, contentDescription = null, tint = Slate500) },
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

                        // Google Drive Link
                        Column {
                            Text("3. Carpeta Google Drive (Fotos HD / Archivos RAW):", style = MaterialTheme.typography.labelSmall, color = Slate600)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = driveUrl,
                                onValueChange = { driveUrl = it },
                                modifier = Modifier.fillMaxWidth().testTag("drive_url_input"),
                                leadingIcon = { Icon(Icons.Default.Folder, contentDescription = null, tint = Slate500) },
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
                    }
                }
            }

            // Copy & Caption Notes
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Copy / Texto de la publicación para revisión:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Incluye la mención a la marca y los hashtags sugeridos.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = captionNotes,
                            onValueChange = { captionNotes = it },
                            modifier = Modifier.fillMaxWidth().height(120.dp).testTag("caption_notes_input"),
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
    }
}
