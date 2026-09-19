package com.example.ui.screens.creator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import com.example.ui.theme.NavyDark
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.SurfaceWhite

@Composable
fun QrCheckInScreen(
    confirmedProposals: List<Proposal>,
    onPerformCheckIn: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeConfirmed = confirmedProposals.filter {
        it.status == ProposalStatus.CONFIRMADO || it.status == ProposalStatus.CHECKED_IN
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = EmeraldDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Pase Digital de Check-in",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Presenta este código QR al llegar al restaurante, hotel o spa para validar tu canje.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
        }

        if (activeConfirmed.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            tint = Slate400,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No tienes visitas confirmadas activas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Text(
                            text = "Cuando un negocio acepte tu propuesta, tu pase QR aparecerá aquí listo para tu visita.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                    }
                }
            }
        } else {
            items(activeConfirmed, key = { it.id }) { proposal ->
                DigitalPassCard(
                    proposal = proposal,
                    onSimulateScan = { onPerformCheckIn(proposal.id) }
                )
            }
        }
    }
}

@Composable
fun DigitalPassCard(
    proposal: Proposal,
    onSimulateScan: () -> Unit
) {
    val isCheckedIn = proposal.status == ProposalStatus.CHECKED_IN

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("digital_pass_card_${proposal.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.5.dp, if (isCheckedIn) EmeraldPrimary else Slate200)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Business and Creator Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(NavyDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("T", color = SurfaceWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Column {
                        Text(
                            text = proposal.businessName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = proposal.city,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                    }
                }

                ProposalStatusChip(status = proposal.status)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Experience Title Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Slate50,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = proposal.experienceTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(14.dp))
                        Text(
                            text = proposal.preferredDate,
                            fontSize = 12.sp,
                            color = Slate700,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Realistic High-Fidelity Custom QR Code Canvas
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceWhite)
                    .border(2.dp, if (isCheckedIn) EmeraldPrimary else NavyDark, RoundedCornerShape(16.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                QrCanvasGraphic(
                    token = proposal.qrToken,
                    modifier = Modifier.fillMaxSize(),
                    isVerified = isCheckedIn
                )

                if (isCheckedIn) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = EmeraldSurface.copy(alpha = 0.95f),
                        border = BorderStroke(1.dp, EmeraldPrimary)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(16.dp))
                            Text(
                                text = "CHECK-IN VERIFICADO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "TOKEN: ${proposal.qrToken}",
                style = MaterialTheme.typography.labelSmall,
                color = Slate500,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Simulation Button
            if (!isCheckedIn) {
                Button(
                    onClick = onSimulateScan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("simulate_checkin_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Validar Check-in en el Negocio",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = EmeraldSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "¡Visita validada! Disfruta la experiencia y prepara tu contenido.",
                            style = MaterialTheme.typography.bodySmall,
                            color = EmeraldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QrCanvasGraphic(
    token: String,
    modifier: Modifier = Modifier,
    isVerified: Boolean = false
) {
    val darkColor = if (isVerified) EmeraldDark else NavyDark

    Canvas(modifier = modifier) {
        val count = 15
        val cellSize = size.width / count

        // Draw 3 QR Corner squares (Top-Left, Top-Right, Bottom-Left)
        drawCornerFinder(0f, 0f, cellSize, darkColor)
        drawCornerFinder((count - 5) * cellSize, 0f, cellSize, darkColor)
        drawCornerFinder(0f, (count - 5) * cellSize, cellSize, darkColor)

        // Draw algorithmic pseudo pattern based on token hash
        val hash = token.hashCode()
        for (r in 0 until count) {
            for (c in 0 until count) {
                // Skip finder areas
                if ((r < 5 && c < 5) || (r < 5 && c >= count - 5) || (r >= count - 5 && c < 5)) continue

                val bit = ((hash xor (r * 31 + c * 17)) and (1 shl ((r + c) % 16))) != 0
                if (bit || (r % 2 == 0 && c % 3 == 0)) {
                    drawRect(
                        color = darkColor,
                        topLeft = Offset(c * cellSize + 1.5f, r * cellSize + 1.5f),
                        size = Size(cellSize - 3f, cellSize - 3f)
                    )
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCornerFinder(
    x: Float,
    y: Float,
    cellSize: Float,
    color: Color
) {
    val finderSize = cellSize * 5
    // Outer border
    drawRect(
        color = color,
        topLeft = Offset(x, y),
        size = Size(finderSize, finderSize)
    )
    // Inner white
    drawRect(
        color = Color.White,
        topLeft = Offset(x + cellSize, y + cellSize),
        size = Size(finderSize - 2 * cellSize, finderSize - 2 * cellSize)
    )
    // Center square
    drawRect(
        color = color,
        topLeft = Offset(x + cellSize * 1.8f, y + cellSize * 1.8f),
        size = Size(finderSize - 3.6f * cellSize, finderSize - 3.6f * cellSize)
    )
}
