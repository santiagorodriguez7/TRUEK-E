package com.example.model

enum class UserRole {
    CREATOR,
    BUSINESS
}

enum class ProposalStatus(
    val labelEs: String,
    val stepIndex: Int
) {
    POSTULADO("Postulado", 1),
    EN_NEGOCIACION("En Negociación", 2),
    CONFIRMADO("Confirmado", 3),
    CHECKED_IN("Check-in Realizado", 4),
    CONTENIDO_ENVIADO("Contenido Enviado", 5),
    CORRECCION_SOLICITADA("Ajustes Solicitados", 5),
    APROBADO("Aprobado", 6),
    COMPLETADO("Completado", 7)
}

data class Category(
    val id: String,
    val name: String,
    val iconName: String,
    val count: Int
)

data class IdealProfile(
    val minFollowers: Int,
    val minEngagementRate: Double,
    val niche: List<String>,
    val preferredFormats: List<String>
)

data class Experience(
    val id: String,
    val title: String,
    val businessName: String,
    val businessCategory: String,
    val location: String,
    val city: String, // e.g. "Bogotá", "Medellín", "Cartagena", "Santa Marta"
    val categoryId: String,
    val estimatedValueCop: Long, // in Colombian Pesos, e.g. 450000L -> "$450.000 COP"
    val matchPercent: Int, // e.g. 98% Match
    val coverImageRes: Int,
    val photos: List<Int>,
    val description: String,
    val whatCreatorReceives: List<String>,
    val deliverablesRequested: List<String>,
    val idealProfile: IdealProfile,
    val datesAvailable: String,
    val companionAllowed: Boolean = true,
    val spotsAvailable: Int = 4,
    val applicantsCount: Int = 3,
    val businessRating: Double = 4.9,
    val reviewsCount: Int = 34,
    val address: String = ""
)

data class CounterProposal(
    val newDate: String,
    val newDeliverables: List<String>,
    val message: String,
    val isPending: Boolean = true
)

data class FeedbackNote(
    val authorName: String,
    val authorRole: UserRole,
    val message: String,
    val timestamp: String
)

data class SubmittedContent(
    val reelUrl: String = "",
    val tiktokUrl: String = "",
    val driveUrl: String = "",
    val captionNotes: String = "",
    val submittedAt: String = "",
    val isApproved: Boolean = false
)

data class Proposal(
    val id: String,
    val experienceId: String,
    val experienceTitle: String,
    val businessName: String,
    val experienceCoverRes: Int,
    val creatorId: String,
    val creatorName: String,
    val creatorHandle: String,
    val creatorAvatarRes: Int,
    val creatorFollowersIg: String,
    val creatorFollowersTt: String,
    val creatorEngagement: String,
    val matchScore: Int,
    val creativeIdea: String,
    val deliverablesSelected: List<String>,
    val preferredDate: String,
    val companionName: String? = null,
    val status: ProposalStatus = ProposalStatus.POSTULADO,
    val counterProposal: CounterProposal? = null,
    val qrToken: String = "TRUEKE-CANJE-${System.currentTimeMillis() % 100000}",
    val submittedContent: SubmittedContent? = null,
    val feedbackHistory: List<FeedbackNote> = emptyList(),
    val ratingGivenToBusiness: Int? = null,
    val ratingGivenToCreator: Int? = null,
    val estimatedValueCop: Long = 0L,
    val location: String = "",
    val city: String = ""
)

data class PortfolioItem(
    val id: String,
    val title: String,
    val businessName: String,
    val imageRes: Int,
    val deliverableType: String,
    val viewsCount: String,
    val likesCount: String
)

data class CreatorProfile(
    val id: String,
    val name: String,
    val handle: String,
    val avatarRes: Int,
    val bio: String,
    val isVerified: Boolean = true,
    val location: String,
    val igFollowers: String,
    val ttFollowers: String,
    val engagementRate: String,
    val completedCanjes: Int,
    val rating: Double,
    val categories: List<String>,
    val portfolioItems: List<PortfolioItem>,
    val audienceTopCities: List<String>,
    val audienceAgeGroup: String
)

data class BusinessProfile(
    val id: String,
    val ownerUserId: String = "",
    val name: String,
    val handle: String,
    val category: String,
    val city: String,
    val address: String = "Zona Rosa / Gastronómica",
    val bio: String = "Experiencias gastronómicas y de hospitalidad premium en Colombia.",
    val rating: Double = 4.9,
    val completedCanjes: Int = 0,
    val activeCanjes: Int = 0,
    val logoRes: Int = 0
)

data class UserAccount(
    val id: String = "",
    val email: String,
    val name: String,
    val role: UserRole,
    val handle: String = "",
    val avatarRes: Int = 0,
    val city: String = "Bogotá",
    val bio: String = ""
)
