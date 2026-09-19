package com.example.data

import com.example.R
import com.example.model.BusinessProfile
import com.example.model.Category
import com.example.model.CounterProposal
import com.example.model.CreatorProfile
import com.example.model.Experience
import com.example.model.FeedbackNote
import com.example.model.IdealProfile
import com.example.model.PortfolioItem
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.model.SubmittedContent
import com.example.model.UserRole

object SampleData {

    val categories = listOf(
        Category("all", "Todos", "explore", 18),
        Category("gastro", "Gastronomía", "restaurant", 6),
        Category("hotel", "Hoteles & Glamping", "hotel", 4),
        Category("cafe", "Cafés de Especialidad", "coffee", 3),
        Category("spa", "Spas & Wellness", "spa", 3),
        Category("turismo", "Turismo & Aventura", "flight", 2)
    )

    val currentCreator = CreatorProfile(
        id = "creator_user",
        name = "Santiago Mora",
        handle = "@santiagomora",
        avatarRes = R.drawable.img_creator_avatar_1787630296671,
        bio = "Creador de contenido de Lifestyle, Gastronomía y Hoteles boutique en Colombia 🇨🇴. Pasión por contar historias auténticas y estética visual premium.",
        isVerified = true,
        location = "Bogotá & Medellín, Colombia",
        igFollowers = "86.4K",
        ttFollowers = "142.8K",
        engagementRate = "5.2%",
        completedCanjes = 32,
        rating = 4.96,
        categories = listOf("Gastronomía", "Hotelería", "Café de Especialidad", "Lifestyle"),
        portfolioItems = listOf(
            PortfolioItem(
                id = "p1",
                title = "Tour Degustación 7 Pasos",
                businessName = "Matiz Restaurante - Bogotá",
                imageRes = R.drawable.img_restaurant_bogota_1787630225112,
                deliverableType = "Reel + 3 Stories",
                viewsCount = "184K",
                likesCount = "14.2K"
            ),
            PortfolioItem(
                id = "p2",
                title = "Fin de Semana en Glamping Domo",
                businessName = "Lumina Luxury Glamping",
                imageRes = R.drawable.img_hotel_glamping_1787630242699,
                deliverableType = "TikTok Viral + Fotos HD",
                viewsCount = "310K",
                likesCount = "28.5K"
            ),
            PortfolioItem(
                id = "p3",
                title = "Ruta del Mejor Café Geisha",
                businessName = "Rituales Café - Medellín",
                imageRes = R.drawable.img_cafe_specialty_1787630260560,
                deliverableType = "Reel 45s",
                viewsCount = "92K",
                likesCount = "8.1K"
            ),
            PortfolioItem(
                id = "p4",
                title = "Día de Spa Botánico y Masaje",
                businessName = "Aura Spa Boutique - Cartagena",
                imageRes = R.drawable.img_spa_wellness_1787630278783,
                deliverableType = "Reel + Carrousel",
                viewsCount = "145K",
                likesCount = "11.6K"
            )
        ),
        audienceTopCities = listOf("Bogotá (48%)", "Medellín (32%)", "Cali (11%)"),
        audienceAgeGroup = "22 - 36 años (84%)"
    )

    val currentBusiness = BusinessProfile(
        id = "biz_matiz",
        name = "Matiz Cocina Andina",
        handle = "@matiz.bogota",
        category = "Alta Gastronomía Colombiana",
        city = "Bogotá",
        address = "Calle 69A # 4-15, Zona G",
        bio = "Restaurante de cocina colombiana de autor y maridaje de autor en la prestigiosa Zona G de Bogotá. Buscamos creadores con audiencia amante de la buena mesa.",
        rating = 4.92,
        completedCanjes = 18,
        activeCanjes = 3,
        logoRes = R.drawable.img_restaurant_bogota_1787630225112
    )

    val initialExperiences = listOf(
        Experience(
            id = "exp_1",
            title = "Cena Degustación de Autor 6 Tiempos + Maridaje",
            businessName = "Matiz Cocina Andina",
            businessCategory = "Gastronomía de Autor",
            location = "Zona G, Bogotá",
            city = "Bogotá",
            categoryId = "gastro",
            estimatedValueCop = 520000L,
            matchPercent = 98,
            coverImageRes = R.drawable.img_restaurant_bogota_1787630225112,
            photos = listOf(
                R.drawable.img_restaurant_bogota_1787630225112,
                R.drawable.img_cafe_specialty_1787630260560
            ),
            description = "Vive una experiencia gastronómica sensorial inspirada en los pisos térmicos de Colombia. Nuestro chef ejecutivo ha creado un menú degustación de 6 pasos con ingredientes locales de pequeños productores campesinos, maridado con cócteles de autor y vinos seleccionados.",
            whatCreatorReceives = listOf(
                "Menú degustación de 6 tiempos para el creador y 1 acompañante (+1)",
                "Maridaje completo con cócteles de autor y vinos premium",
                "Mesa VIP con mejor iluminación para tomas y fotografía",
                "Conversación exclusiva con el Chef Ejecutivo sobre la historia de los platos"
            ),
            deliverablesRequested = listOf(
                "1 Reel / TikTok (30-60 seg) dinámico mostrando la experiencia y platos destacados",
                "3 Historias de Instagram en tiempo real con mención @matiz.bogota y sticker de ubicación",
                "3 Fotografías en alta resolución para uso de la marca en sus redes sociales",
                "Entrega de material antes de 5 días hábiles posteriores a la visita"
            ),
            idealProfile = IdealProfile(
                minFollowers = 25000,
                minEngagementRate = 3.5,
                niche = listOf("Foodies", "Lifestyle", "Planes Bogotá", "Alta Cocina"),
                preferredFormats = listOf("Reels estéticos", "Storytelling en video", "Fotos profesionales")
            ),
            datesAvailable = "Jueves a Sábados (7:00 PM o 8:30 PM)",
            companionAllowed = true,
            spotsAvailable = 2,
            applicantsCount = 5,
            businessRating = 4.9,
            reviewsCount = 42,
            address = "Cl. 69A #4-15, Zona G, Bogotá"
        ),
        Experience(
            id = "exp_2",
            title = "Estadía 2 Noches en Glamping Domo Deluxe + Jacuzzi",
            businessName = "Lumina Luxury Glamping",
            businessCategory = "Hotelería & Glamping",
            location = "Guatavita, Cundinamarca",
            city = "Bogotá",
            categoryId = "hotel",
            estimatedValueCop = 1350000L,
            matchPercent = 95,
            coverImageRes = R.drawable.img_hotel_glamping_1787630242699,
            photos = listOf(
                R.drawable.img_hotel_glamping_1787630242699,
                R.drawable.img_spa_wellness_1787630278783
            ),
            description = "Desconéctate en un domo geodésico de lujo frente a la laguna de Guatavita. Incluye jacuzzi privado climatizado, cama king size con vista a las estrellas, fogata nocturna y desayuno campestre artesanal.",
            whatCreatorReceives = listOf(
                "Hospedaje de 2 noches y 3 días para 2 personas en Domo Deluxe",
                "Desayuno campestre gourmet ambos días",
                "Botella de vino de bienvenida y kit de masmelos para la fogata",
                "Uso libre e ilimitado de jacuzzi privado climatizado"
            ),
            deliverablesRequested = listOf(
                "1 Reel de experiencia completa (recorrido, jacuzzi, atardecer, amanecer)",
                "1 TikTok estilo vlog 'Un plan inolvidable cerca a Bogotá'",
                "5 Historias destacadas con enlace de reserva y código de descuento exclusivo",
                "5 Fotos en formato vertical HD para uso publicitario"
            ),
            idealProfile = IdealProfile(
                minFollowers = 40000,
                minEngagementRate = 4.0,
                niche = listOf("Viajes & Turismo", "Parejas & Planes", "Naturaleza", "Lifestyle"),
                preferredFormats = listOf("Vlog travel", "Tomas estéticas con dron/4K", "POV Experiencia")
            ),
            datesAvailable = "Domingo a Jueves (Sujeto a disponibilidad)",
            companionAllowed = true,
            spotsAvailable = 3,
            applicantsCount = 9,
            businessRating = 4.95,
            reviewsCount = 68,
            address = "Vereda Montecillo, Guatavita (A 1h 30m de Bogotá)"
        ),
        Experience(
            id = "exp_3",
            title = "Cata de Cafés Especiales + Brunch Completo para 2",
            businessName = "Origen & Alquimia Coffee Bar",
            businessCategory = "Cafés de Especialidad",
            location = "El Poblado, Medellín",
            city = "Medellín",
            categoryId = "cafe",
            estimatedValueCop = 280000L,
            matchPercent = 92,
            coverImageRes = R.drawable.img_cafe_specialty_1787630260560,
            photos = listOf(
                R.drawable.img_cafe_specialty_1787630260560,
                R.drawable.img_restaurant_bogota_1787630225112
            ),
            description = "Aprende a catar café como un barista profesional. Disfruta una cata guiada de 3 variedades de café colombiano (Geisha, Borbón Rosado y Wush Wush) seguida de nuestro afamado brunch artesanal.",
            whatCreatorReceives = listOf(
                "Cata privada sensorial guiada por Barista Q-Grader (45 min)",
                "Brunch premium completo a la carta para 2 personas",
                "Bolsa de 340g de café de origen tostado fresco para llevar a casa"
            ),
            deliverablesRequested = listOf(
                "1 Reel didáctico y estético sobre cómo catar café de especialidad",
                "4 Historias de Instagram con reseña del brunch y café favorito",
                "Mención activa a la ubicación en El Poblado"
            ),
            idealProfile = IdealProfile(
                minFollowers = 15000,
                minEngagementRate = 3.0,
                niche = listOf("Café", "Foodies Medellín", "Lifestyle", "Brunch"),
                preferredFormats = listOf("Reels dinámicos", "Audio tendencia")
            ),
            datesAvailable = "Lunes a Viernes (9:00 AM a 2:00 PM)",
            companionAllowed = true,
            spotsAvailable = 4,
            applicantsCount = 7,
            businessRating = 4.88,
            reviewsCount = 53,
            address = "Cra. 37 # 8A-29, Vía Primavera, El Poblado, Medellín"
        ),
        Experience(
            id = "exp_4",
            title = "Ritual Hidroterapia Botánica + Masaje Relajante en Pareja",
            businessName = "Aura Wellness Spa Boutique",
            businessCategory = "Spas & Wellness",
            location = "Ciudad Amurallada, Cartagena",
            city = "Cartagena",
            categoryId = "spa",
            estimatedValueCop = 780000L,
            matchPercent = 89,
            coverImageRes = R.drawable.img_spa_wellness_1787630278783,
            photos = listOf(
                R.drawable.img_spa_wellness_1787630278783,
                R.drawable.img_hotel_glamping_1787630242699
            ),
            description = "Un oasis de tranquilidad dentro de una casona colonial del siglo XVIII. Incluye circuito de hidroterapia con sales marinas, exfoliación con café y cacao colombiano, y masaje corporal de 60 minutos con aromaterapia.",
            whatCreatorReceives = listOf(
                "Circuito de aguas y piscina termal privada (40 min)",
                "Masaje corporal relajante de 60 min para creador y acompañante",
                "Copa de espumoso y tabla de frutas tropicales de cortesía"
            ),
            deliverablesRequested = listOf(
                "1 Reel estético 'Relaxing aesthetic spa day in Cartagena'",
                "3 Historias en el spa destacando la atmósfera colonial y relajación",
                "Fotografías de ambiente para uso en web del spa"
            ),
            idealProfile = IdealProfile(
                minFollowers = 30000,
                minEngagementRate = 3.8,
                niche = listOf("Wellness", "Selfcare", "Cartagena Travel", "Luxury"),
                preferredFormats = listOf("Reels cinematográficos", "Voz en off tranquila")
            ),
            datesAvailable = "Todos los días con reserva previa (10:00 AM - 6:00 PM)",
            companionAllowed = true,
            spotsAvailable = 2,
            applicantsCount = 6,
            businessRating = 4.97,
            reviewsCount = 49,
            address = "Calle del Curato # 38-99, Centro Histórico, Cartagena"
        )
    )

    val initialProposals = listOf(
        // Proposal 1: In negotiation / counter-proposal
        Proposal(
            id = "prop_1",
            experienceId = "exp_1",
            experienceTitle = "Cena Degustación de Autor 6 Tiempos + Maridaje",
            businessName = "Matiz Cocina Andina",
            experienceCoverRes = R.drawable.img_restaurant_bogota_1787630225112,
            creatorId = "creator_vale",
            creatorName = "Valeria Charris",
            creatorHandle = "@valecharris.foodie",
            creatorAvatarRes = R.drawable.img_creator_avatar_1787630296671,
            creatorFollowersIg = "86.4K",
            creatorFollowersTt = "142.8K",
            creatorEngagement = "5.2%",
            matchScore = 98,
            creativeIdea = "Mi propuesta es un Reel estilo 'POV: Probando el menú degustación más exclusivo de la Zona G en Bogotá'. Enfocaré la narrativa en el maridaje con destilados colombianos y la estética de los platos en primer plano con iluminación profesional.",
            deliverablesSelected = listOf(
                "1 Reel de 45 segundos con gancho de alto impacto",
                "3 Historias de Instagram con mención y sticker",
                "4 Fotos en alta calidad editadas en formato vertical"
            ),
            preferredDate = "Viernes 28 de Agosto, 8:00 PM",
            companionName = "Camilo Restrepo (Videógrafo)",
            status = ProposalStatus.EN_NEGOCIACION,
            counterProposal = CounterProposal(
                newDate = "Sábado 29 de Agosto, 7:30 PM",
                newDeliverables = listOf(
                    "1 Reel de 45 segundos enfocado en los 6 tiempos",
                    "3 Historias en vivo durante la cena",
                    "1 Mención especial al cóctel insignia de la casa 'Selva Andina'"
                ),
                message = "¡Hola! Nos encanta tu propuesta y tu tasa de interacción. El viernes 28 tenemos el restaurante reservado para un evento privado, pero te proponemos con gusto el sábado 29 a las 7:30 PM en nuestra mejor mesa. ¿Te funciona esta fecha?",
                isPending = true
            ),
            feedbackHistory = listOf(
                FeedbackNote("Creador", UserRole.CREATOR, "Propuesta enviada con enfoque en maridaje andino.", "24 Ago, 10:15 AM"),
                FeedbackNote("Matiz Cocina Andina", UserRole.BUSINESS, "Hola, enviamos contrapropuesta con cambio de fecha al sábado 29.", "24 Ago, 2:30 PM")
            ),
            estimatedValueCop = 520000L,
            location = "Zona G, Bogotá",
            city = "Bogotá"
        ),
        // Proposal 2: Confirmed (Ready for Visit / QR checkin)
        Proposal(
            id = "prop_2",
            experienceId = "exp_2",
            experienceTitle = "Estadía 2 Noches en Glamping Domo Deluxe + Jacuzzi",
            businessName = "Lumina Luxury Glamping",
            experienceCoverRes = R.drawable.img_hotel_glamping_1787630242699,
            creatorId = "creator_vale",
            creatorName = "Valeria Charris",
            creatorHandle = "@valecharris.foodie",
            creatorAvatarRes = R.drawable.img_creator_avatar_1787630296671,
            creatorFollowersIg = "86.4K",
            creatorFollowersTt = "142.8K",
            creatorEngagement = "5.2%",
            matchScore = 95,
            creativeIdea = "Haré un Reel con formato '3 razones por las que este glamping en Guatavita parece sacado de un cuento'. Tomas estéticas al atardecer en el jacuzzi, vista a la laguna y fogata nocturna.",
            deliverablesSelected = listOf(
                "1 Reel de experiencia completa en 4K",
                "1 TikTok estilo Vlog de escapada",
                "5 Historias con sticker de reserva"
            ),
            preferredDate = "10 al 12 de Septiembre",
            companionName = "Acompañante de viaje",
            status = ProposalStatus.CONFIRMADO,
            qrToken = "TRUEKE-LUMINA-GLAMP-84920",
            feedbackHistory = listOf(
                FeedbackNote("Creador", UserRole.CREATOR, "Propuesta de fin de semana enviada.", "22 Ago, 09:00 AM"),
                FeedbackNote("Lumina Luxury Glamping", UserRole.BUSINESS, "¡Aceptamos tu propuesta! Domo Deluxe reservado para las fechas indicadas. ¡Te esperamos!", "22 Ago, 04:20 PM")
            ),
            estimatedValueCop = 1350000L,
            location = "Guatavita, Cundinamarca",
            city = "Bogotá"
        ),
        // Proposal 3: Content Submitted / In Review by Business
        Proposal(
            id = "prop_3",
            experienceId = "exp_3",
            experienceTitle = "Cata de Cafés Especiales + Brunch Completo para 2",
            businessName = "Origen & Alquimia Coffee Bar",
            experienceCoverRes = R.drawable.img_cafe_specialty_1787630260560,
            creatorId = "creator_vale",
            creatorName = "Valeria Charris",
            creatorHandle = "@valecharris.foodie",
            creatorAvatarRes = R.drawable.img_creator_avatar_1787630296671,
            creatorFollowersIg = "86.4K",
            creatorFollowersTt = "142.8K",
            creatorEngagement = "5.2%",
            matchScore = 92,
            creativeIdea = "Reel educativo '¿Cómo saber si estás tomando café de calidad?' grabado durante la cata en Origen & Alquimia, mostrando los métodos de filtrado Chemex y V60.",
            deliverablesSelected = listOf(
                "1 Reel de 45 segundos",
                "4 Historias de Instagram",
                "Mención con sticker de ubicación"
            ),
            preferredDate = "18 de Agosto, 10:30 AM",
            companionName = "Mariana Ríos",
            status = ProposalStatus.CONTENIDO_ENVIADO,
            submittedContent = SubmittedContent(
                reelUrl = "https://instagram.com/reel/C8k9xL2pQ1A",
                tiktokUrl = "https://tiktok.com/@valecharris/video/73918239102",
                driveUrl = "https://drive.google.com/drive/folders/trueke-origen-cafe-raw-assets",
                captionNotes = "Borrador de copy: ¿Sabías que el café Geisha colombiano tiene notas a jazmín y durazno? ☕✨ Viví la experiencia de cata en @origenyalquimia en El Poblado, Medellín. Guarda este plan para tu próximo fin de semana!",
                submittedAt = "23 Ago, 11:40 AM",
                isApproved = false
            ),
            feedbackHistory = listOf(
                FeedbackNote("Origen & Alquimia", UserRole.BUSINESS, "Check-in realizado en tienda el 18 de Agosto.", "18 Ago, 10:45 AM"),
                FeedbackNote("Valeria Charris", UserRole.CREATOR, "Borrador de Reel y fotos subidos a revisión.", "23 Ago, 11:40 AM")
            ),
            estimatedValueCop = 280000L,
            location = "El Poblado, Medellín",
            city = "Medellín"
        ),
        // Proposal 4: Completed
        Proposal(
            id = "prop_4",
            experienceId = "exp_4",
            experienceTitle = "Ritual Hidroterapia Botánica + Masaje Relajante en Pareja",
            businessName = "Aura Wellness Spa Boutique",
            experienceCoverRes = R.drawable.img_spa_wellness_1787630278783,
            creatorId = "creator_vale",
            creatorName = "Valeria Charris",
            creatorHandle = "@valecharris.foodie",
            creatorAvatarRes = R.drawable.img_creator_avatar_1787630296671,
            creatorFollowersIg = "86.4K",
            creatorFollowersTt = "142.8K",
            creatorEngagement = "5.2%",
            matchScore = 89,
            creativeIdea = "Reel cinemático de relajación en Cartagena.",
            deliverablesSelected = listOf(
                "1 Reel de 60s",
                "3 Historias en vivo",
                "Fotos de alta resolución"
            ),
            preferredDate = "5 de Agosto",
            companionName = "Andrés Gómez",
            status = ProposalStatus.COMPLETADO,
            submittedContent = SubmittedContent(
                reelUrl = "https://instagram.com/reel/C8a12Z9mB8",
                tiktokUrl = "https://tiktok.com/@valecharris/video/7389102830",
                driveUrl = "https://drive.google.com/drive/folders/aura-spa-final",
                captionNotes = "El mejor spa escondido en la Ciudad Amurallada de Cartagena 🌿🧖‍♀️ @auraspaboutique",
                submittedAt = "8 Ago",
                isApproved = true
            ),
            ratingGivenToBusiness = 5,
            ratingGivenToCreator = 5,
            feedbackHistory = listOf(
                FeedbackNote("Aura Wellness Spa", UserRole.BUSINESS, "¡Excelente contenido! Aprobado sin cambios.", "9 Ago, 10:00 AM"),
                FeedbackNote("Valeria Charris", UserRole.CREATOR, "Publicación realizada y métricas registradas.", "10 Ago, 03:00 PM")
            ),
            estimatedValueCop = 780000L,
            location = "Ciudad Amurallada, Cartagena",
            city = "Cartagena"
        )
    )

    val sampleApplicantCreators = listOf(
        CreatorProfile(
            id = "creator_camila",
            name = "Camila Restrepo",
            handle = "@camilarestrepo.foodie",
            avatarRes = R.drawable.img_creator_avatar_1787630296671,
            bio = "Foodie & Lifestyle creator en Bogotá y Medellín. Especialista en gastronomía y turismo boutique.",
            isVerified = true,
            location = "Bogotá, Colombia",
            igFollowers = "86.4K",
            ttFollowers = "142.8K",
            engagementRate = "5.2%",
            completedCanjes = 32,
            rating = 4.96,
            categories = listOf("Gastronomía", "Hotelería", "Cafés"),
            portfolioItems = emptyList(),
            audienceTopCities = listOf("Bogotá (48%)", "Medellín (32%)"),
            audienceAgeGroup = "22-35"
        ),
        CreatorProfile(
            id = "creator_felipe",
            name = "Felipe & Dani Travel",
            handle = "@felipedaniviajes",
            avatarRes = R.drawable.img_hotel_glamping_1787630242699,
            bio = "Viajeros colombianos recorriendo los mejores glampings y hoteles del país 🇨🇴.",
            isVerified = true,
            location = "Medellín, Colombia",
            igFollowers = "115.0K",
            ttFollowers = "230.5K",
            engagementRate = "6.1%",
            completedCanjes = 45,
            rating = 4.98,
            categories = listOf("Hoteles & Glamping", "Turismo", "Aventura"),
            portfolioItems = emptyList(),
            audienceTopCities = listOf("Medellín (52%)", "Bogotá (30%)"),
            audienceAgeGroup = "24-40"
        ),
        CreatorProfile(
            id = "creator_sofia",
            name = "Sofía Gastro",
            handle = "@sofiagastro.co",
            avatarRes = R.drawable.img_cafe_specialty_1787630260560,
            bio = "Reseñas honestas de café, brunch y restaurantes en Colombia.",
            isVerified = false,
            location = "Bogotá, Colombia",
            igFollowers = "34.2K",
            ttFollowers = "58.0K",
            engagementRate = "4.4%",
            completedCanjes = 14,
            rating = 4.85,
            categories = listOf("Cafés", "Gastronomía", "Brunch"),
            portfolioItems = emptyList(),
            audienceTopCities = listOf("Bogotá (65%)", "Cali (15%)"),
            audienceAgeGroup = "18-30"
        )
    )
}
