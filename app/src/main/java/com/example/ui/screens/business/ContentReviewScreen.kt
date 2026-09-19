package com.example.ui.screens.business

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.SurfaceWhite

@Composable
fun ContentReviewScreen(
    proposalsWithContent: List<Proposal>,
    onApproveContent: (String) -> Unit,
    onRequestCorrections: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var correctionDialogProposal by remember { mutableStateOf<Proposal?>(null) }

    val contentList = proposalsWithContent.filter {
        it.status == ProposalStatus.CONTENIDO_ENVIADO ||
                it.status == ProposalStatus.CORRECCION_SOLICITADA ||
                it.status == ProposalStatus.APROBADO ||
                it.status == ProposalStatus.COMPLETADO
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Revisión de Contenido",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Revisa los borradores de Reels, historias y fotos antes de que el creador publique en vivo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
        }

        if (contentList.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.RateReview, contentDescription = null, tint = Slate400, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No hay contenido pendiente de revisión",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Cuando los creadores suban sus borradores de reels o fotos, aparecerán aquí para tu aprobación.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                    }
                }
            }
        } else {
            items(contentList, key = { it.id }) { proposal ->
                BusinessContentReviewCard(
                    proposal = proposal,
                    onApprove = { onApproveContent(proposal.id) },
                    onRequestCorrection = { correctionDialogProposal = proposal }
                )
            }
        }
    }

    // Request Correction Dialog
    correctionDialogProposal?.let { prop ->
        BusinessCorrectionDialog(
            proposal = prop,
            onDismiss = { correctionDialogProposal = null },
            onSend = { note ->
                onRequestCorrections(prop.id, note)
                correctionDialogProposal = null
            }
        )
    }
}

@Composable
fun BusinessContentReviewCard(
    proposal: Proposal,
    onApprove: () -> Unit,
    onRequestCorrection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val content = proposal.submittedContent

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("content_review_card_${proposal.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with Creator & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = proposal.creatorAvatarRes),
                        contentDescription = proposal.creatorName,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(proposal.creatorName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(proposal.creatorHandle, style = MaterialTheme.typography.bodySmall, color = Slate500)
                    }
                }

                ProposalStatusChip(status = proposal.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Experience targeted
            Text(
                text = "Experiencia: ${proposal.experienceTitle}",
                style = MaterialTheme.typography.labelSmall,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Drafts links container
            if (content != null) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Slate50,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Borradores y Archivos Enviados:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyDark)

                        // Reel
                        if (content.reelUrl.isNotBlank()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.Videocam, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(16.dp))
                                    Text("Instagram Reel (Borrador)", fontSize = 12.sp, color = Slate800, fontWeight = FontWeight.Medium)
                                }
                                Text(content.reelUrl.take(24) + "...", fontSize = 11.sp, color = EmeraldDark)
                            }
                        }

                        // TikTok
                        if (content.tiktokUrl.isNotBlank()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.Link, contentDescription = null, tint = NavyDark, modifier = Modifier.size(16.dp))
                                    Text("TikTok (Borrador)", fontSize = 12.sp, color = Slate800, fontWeight = FontWeight.Medium)
                                }
                                Text(content.tiktokUrl.take(24) + "...", fontSize = 11.sp, color = NavyDark)
                            }
                        }

                        // Drive
                        if (content.driveUrl.isNotBlank()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.Folder, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                    Text("Google Drive (Fotos RAW)", fontSize = 12.sp, color = Slate800, fontWeight = FontWeight.Medium)
                                }
                                Text("Abrir Carpeta", fontSize = 11.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Caption / Copy Draft
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = EmeraldSurface.copy(alpha = 0.5f),
                    border = BorderStroke(0.8.dp, EmeraldLight.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Texto de la publicación (Copy):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = content.captionNotes,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate800,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            if (proposal.status == ProposalStatus.CONTENIDO_ENVIADO || proposal.status == ProposalStatus.CORRECCION_SOLICITADA) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onRequestCorrection,
                        modifier = Modifier.weight(1f).height(44.dp).testTag("request_correction_btn_${proposal.id}"),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, CoralAccent)
                    ) {
                        Text("Pedir Ajustes", fontSize = 12.sp, color = CoralAccent, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onApprove,
                        modifier = Modifier.weight(1.2f).height(44.dp).testTag("approve_content_btn_${proposal.id}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Aprobar Contenido", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            } else if (proposal.status == ProposalStatus.APROBADO) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = EmeraldSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Contenido Aprobado • Esperando que el creador publique en vivo", fontSize = 12.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCorrectionDialog(
    proposal: Proposal,
    onDismiss: () -> Unit,
    onSend: (String) -> Unit
) {
    var correctionText by remember {
        mutableStateOf("El video se ve espectacular. Por favor asegúrate de incluir el sticker interactivo de ubicación de nuestro restaurante en Bogotá y de pronunciar el nombre del plato estrella en la narración.")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onSend(correctionText) },
                colors = ButtonDefaults.buttonColors(containerColor = CoralAccent)
            ) {
                Text("Enviar Ajustes al Creador")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = {
            Text("Solicitar Correcciones", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Describe con claridad qué ajustes necesita el creador para aprobar su publicación:",
                    fontSize = 12.sp,
                    color = Slate700
                )
                OutlinedTextField(
                    value = correctionText,
                    onValueChange = { correctionText = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    )
}
