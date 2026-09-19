package com.example.ui.screens.creator

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldSurface
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

@Composable
fun MyCanjesScreen(
    proposals: List<Proposal>,
    onOpenProposalNegotiation: (Proposal) -> Unit,
    onOpenQrCheckIn: (Proposal) -> Unit,
    onOpenContentSubmission: (Proposal) -> Unit,
    onOpenCompleteRegistration: (Proposal) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("all") }
    val filters = listOf(
        "all" to "Todos (${proposals.size})",
        "negotiating" to "En Negociación",
        "confirmed" to "Confirmados",
        "in_review" to "En Revisión",
        "completed" to "Completados"
    )

    val filteredList = proposals.filter { prop ->
        when (selectedFilter) {
            "all" -> true
            "negotiating" -> prop.status == ProposalStatus.EN_NEGOCIACION || prop.status == ProposalStatus.POSTULADO
            "confirmed" -> prop.status == ProposalStatus.CONFIRMADO || prop.status == ProposalStatus.CHECKED_IN
            "in_review" -> prop.status == ProposalStatus.CONTENIDO_ENVIADO || prop.status == ProposalStatus.CORRECCION_SOLICITADA || prop.status == ProposalStatus.APROBADO
            "completed" -> prop.status == ProposalStatus.COMPLETADO
            else -> true
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Mis Canjes TRUEK-E",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Gestiona tus propuestas, negociaciones, check-in y entregas de contenido.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
        }

        // Filter Pills
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(filters) { (key, label) ->
                    val isSelected = selectedFilter == key
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) NavyDark else SurfaceWhite,
                        border = BorderStroke(1.dp, if (isSelected) NavyDark else Slate200),
                        modifier = Modifier.clickable { selectedFilter = key }
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) SurfaceWhite else Slate700,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        if (filteredList.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Handshake,
                            contentDescription = null,
                            tint = Slate400,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No tienes canjes en este estado",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Explora el marketplace y postúlate a experiencias increíbles.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                    }
                }
            }
        } else {
            items(filteredList, key = { it.id }) { proposal ->
                CreatorProposalCard(
                    proposal = proposal,
                    onOpenNegotiation = { onOpenProposalNegotiation(proposal) },
                    onOpenQrCheckIn = { onOpenQrCheckIn(proposal) },
                    onOpenContentSubmission = { onOpenContentSubmission(proposal) },
                    onOpenCompleteRegistration = { onOpenCompleteRegistration(proposal) }
                )
            }
        }
    }
}

@Composable
fun CreatorProposalCard(
    proposal: Proposal,
    onOpenNegotiation: () -> Unit,
    onOpenQrCheckIn: () -> Unit,
    onOpenContentSubmission: () -> Unit,
    onOpenCompleteRegistration: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("proposal_card_${proposal.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Status Chip & Match
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProposalStatusChip(status = proposal.status)

                Text(
                    text = "${proposal.matchScore}% Match",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Experience Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    Text(
                        text = proposal.businessName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Slate500
                    )
                    Text(
                        text = proposal.experienceTitle,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    Text(
                        text = "Valor: ${TruekeViewModel.formatCop(proposal.estimatedValueCop)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = EmeraldDark,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Preferred Date & Entregables count
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate50)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Slate500, modifier = Modifier.size(14.dp))
                    Text(proposal.preferredDate, fontSize = 12.sp, color = Slate700)
                }
                Text(
                    text = "${proposal.deliverablesSelected.size} entregables",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate600
                )
            }

            // Contextual Action Button based on Status
            Spacer(modifier = Modifier.height(12.dp))

            when (proposal.status) {
                ProposalStatus.EN_NEGOCIACION -> {
                    Button(
                        onClick = onOpenNegotiation,
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                    ) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ver Contrapropuesta de la Marca", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }

                ProposalStatus.CONFIRMADO -> {
                    Button(
                        onClick = onOpenQrCheckIn,
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                    ) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = EmeraldLight, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Abrir Pase QR de Check-in", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }

                ProposalStatus.CHECKED_IN, ProposalStatus.CORRECCION_SOLICITADA -> {
                    Button(
                        onClick = onOpenContentSubmission,
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            if (proposal.status == ProposalStatus.CORRECCION_SOLICITADA) "Ver Ajustes y Resubir" else "Subir Contenido a Revisión",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                ProposalStatus.CONTENIDO_ENVIADO -> {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Slate100,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "⏳ Contenido en revisión por ${proposal.businessName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate700,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                ProposalStatus.APROBADO -> {
                    Button(
                        onClick = onOpenCompleteRegistration,
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Registrar Publicación & Calificar", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }

                ProposalStatus.COMPLETADO -> {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = EmeraldSurface,
                        border = BorderStroke(1.dp, EmeraldLight.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Canje Completado con Éxito ★ Calificado",
                                style = MaterialTheme.typography.bodySmall,
                                color = EmeraldDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                ProposalStatus.POSTULADO -> {
                    OutlinedButton(
                        onClick = onOpenNegotiation,
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Slate300)
                    ) {
                        Text("Ver Detalles de Postulación", fontSize = 13.sp, color = NavyDark, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
fun NegotiationDetailDialog(
    proposal: Proposal,
    onDismiss: () -> Unit,
    onAcceptCounterProposal: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (proposal.counterProposal != null && proposal.status == ProposalStatus.EN_NEGOCIACION) {
                Button(
                    onClick = { onAcceptCounterProposal(proposal.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                ) {
                    Text("Aceptar Contrapropuesta")
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Volver")
            }
        },
        title = {
            Text(
                text = "Estado de Negociación",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = proposal.experienceTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = NavyDark,
                    fontWeight = FontWeight.Bold
                )

                if (proposal.counterProposal != null) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GoldSurface,
                        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Contrapropuesta de ${proposal.businessName}:",
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = proposal.counterProposal.message,
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate800,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📅 Nueva fecha propuesta: ${proposal.counterProposal.newDate}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = NavyDark
                            )
                        }
                    }
                }

                Text(
                    text = "Historial de mensajes:",
                    style = MaterialTheme.typography.labelSmall,
                    color = Slate500
                )

                proposal.feedbackHistory.forEach { note ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Slate50,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(note.authorName, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NavyDark)
                                Text(note.timestamp, fontSize = 10.sp, color = Slate400)
                            }
                            Text(note.message, fontSize = 12.sp, color = Slate700)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CompleteCollaborationDialog(
    proposal: Proposal,
    onDismiss: () -> Unit,
    onComplete: (Int, Int, String) -> Unit
) {
    var ratingToBusiness by remember { mutableIntStateOf(5) }
    var reviewText by remember {
        mutableStateOf("¡Una experiencia increíble y un trato impecable por parte de todo el equipo! Super recomendado para colaboraciones.")
    }
    var livePostLink by remember { mutableStateOf("https://instagram.com/reel/live_published_post_colombia") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onComplete(ratingToBusiness, 5, reviewText) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
            ) {
                Text("Completar Canje")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = {
            Text("Registrar Publicación & Calificar", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("1. Enlace de la publicación en vivo:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = livePostLink,
                    onValueChange = { livePostLink = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Text("2. Califica tu experiencia con ${proposal.businessName}:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { star ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "$star estrellas",
                            tint = if (star <= ratingToBusiness) GoldAccent else Slate300,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { ratingToBusiness = star }
                        )
                    }
                }

                Text("3. Reseña sobre la colaboración:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    )
}
