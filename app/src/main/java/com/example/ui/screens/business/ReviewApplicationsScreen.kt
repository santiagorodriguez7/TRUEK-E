package com.example.ui.screens.business

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
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
import com.example.ui.components.MatchBadge
import com.example.ui.components.ProposalStatusChip
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

@Composable
fun ReviewApplicationsScreen(
    proposals: List<Proposal>,
    onAcceptProposal: (String) -> Unit,
    onSendCounterProposal: (String, String, String) -> Unit,
    onViewCreatorProfile: (Proposal) -> Unit,
    modifier: Modifier = Modifier
) {
    var counterProposalDialogProposal by remember { mutableStateOf<Proposal?>(null) }

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
                    text = "Postulaciones Recibidas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Evalúa el perfil de los creadores, sus propuestas creativas y confirma el canje.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
        }

        if (proposals.isEmpty()) {
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
                        Icon(Icons.Default.Group, contentDescription = null, tint = Slate400, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No hay postulaciones pendientes",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tus experiencias activas pronto recibirán postulaciones de creadores verificados.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                    }
                }
            }
        } else {
            items(proposals, key = { it.id }) { proposal ->
                BusinessApplicationCard(
                    proposal = proposal,
                    onAccept = { onAcceptProposal(proposal.id) },
                    onCounterProposal = { counterProposalDialogProposal = proposal },
                    onViewProfile = { onViewCreatorProfile(proposal) }
                )
            }
        }
    }

    // Counter Proposal Dialog
    counterProposalDialogProposal?.let { prop ->
        BusinessCounterProposalDialog(
            proposal = prop,
            onDismiss = { counterProposalDialogProposal = null },
            onSend = { newDate, msg ->
                onSendCounterProposal(prop.id, newDate, msg)
                counterProposalDialogProposal = null
            }
        )
    }
}

@Composable
fun BusinessApplicationCard(
    proposal: Proposal,
    onAccept: () -> Unit,
    onCounterProposal: () -> Unit,
    onViewProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("application_card_${proposal.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Creator Profile Snippet & Match Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.clickable { onViewProfile() }
                ) {
                    Image(
                        painter = painterResource(id = proposal.creatorAvatarRes),
                        contentDescription = proposal.creatorName,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, EmeraldPrimary, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(proposal.creatorName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.Verified, contentDescription = "Verificado", tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                        }
                        Text(proposal.creatorHandle, style = MaterialTheme.typography.bodySmall, color = Slate500)
                    }
                }

                MatchBadge(matchPercent = proposal.matchScore)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Metrics Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate50)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Instagram", fontSize = 10.sp, color = Slate500)
                    Text(proposal.creatorFollowersIg, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PurpleAccent)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TikTok", fontSize = 10.sp, color = Slate500)
                    Text(proposal.creatorFollowersTt, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyDark)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Engagement", fontSize = 10.sp, color = Slate500)
                    Text(proposal.creatorEngagement, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Canjes", fontSize = 10.sp, color = Slate500)
                    Text("32", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate700)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Experience targeted
            Text(
                text = "Para: ${proposal.experienceTitle}",
                style = MaterialTheme.typography.labelSmall,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Creative Hook Proposed Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GoldSurface.copy(alpha = 0.6f),
                border = BorderStroke(0.8.dp, GoldAccent.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
                        Text("Propuesta Creativa & Gancho:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NavyDark)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = proposal.creativeIdea,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate800,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Preferred Date & Entregables
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Slate500, modifier = Modifier.size(14.dp))
                    Text(proposal.preferredDate, fontSize = 12.sp, color = Slate700)
                }
                proposal.companionName?.let {
                    Text("• $it", fontSize = 11.sp, color = Slate500)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            if (proposal.status == ProposalStatus.POSTULADO || proposal.status == ProposalStatus.EN_NEGOCIACION) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onCounterProposal,
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Slate300)
                    ) {
                        Text("Contrapropuesta", fontSize = 12.sp, color = NavyDark)
                    }

                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f).height(42.dp).testTag("accept_proposal_btn_${proposal.id}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldLight, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Aceptar Canje", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                ProposalStatusChip(status = proposal.status)
            }
        }
    }
}

@Composable
fun BusinessCounterProposalDialog(
    proposal: Proposal,
    onDismiss: () -> Unit,
    onSend: (newDate: String, message: String) -> Unit
) {
    var newDate by remember { mutableStateOf("Sábado próximo a las 2:00 PM") }
    var counterMessage by remember {
        mutableStateOf("Nos encanta tu propuesta visual. Nos gustaría proponerte realizar la experiencia el sábado en el almuerzo y solicitamos incluir 1 TikTok adicional.")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onSend(newDate, counterMessage) },
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
            ) {
                Text("Enviar Contrapropuesta")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = {
            Text("Enviar Contrapropuesta a ${proposal.creatorName}", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Nueva fecha y hora sugerida:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = newDate,
                    onValueChange = { newDate = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Text("Mensaje y ajustes sugeridos:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = counterMessage,
                    onValueChange = { counterMessage = it },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    )
}
