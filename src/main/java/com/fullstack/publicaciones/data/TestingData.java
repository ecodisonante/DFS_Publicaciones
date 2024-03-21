package com.fullstack.publicaciones.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fullstack.publicaciones.model.Comment;
import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.model.Usuario;


@Component
public class TestingData {

    Usuario musicfreak = new Usuario("musicfreak", "David Melódico", "david_melodico@example.com", "clave123");
    Usuario foodiegirl = new Usuario("foodiegirl", "Sofía Sabrosa", "sofia_sabrosa@example.com", "contraseña456");
    Usuario travelbug22 = new Usuario("travelbug22", "Carlos Viajero", "carlos_viajero@example.com", "secreto789");
    Usuario artlover = new Usuario("artlover", "Elena Artística", "elena_artistica@example.com", "password123");
    Usuario fitnessfanatic = new Usuario("fitnessfanatic", "Diego Fitness", "diego_fitness@example.com", "contraseña456");
    Usuario techgeek = new Usuario("techgeek", "Laura Tecnológica", "laura_tecnologica@example.com", "secreto789");

    List<Usuario> usuarios = Arrays.asList(musicfreak, foodiegirl, travelbug22, artlover, fitnessfanatic, techgeek);

    Publicacion pub1 = new Publicacion(1, "Los 10 álbumes esenciales de la década", "Descubre las joyas musicales que definieron los últimos años.", "En esta lista, exploraremos diez álbumes que han dejado una marca indeleble en la última década. Desde el pop hasta el indie, ¡prepárate para descubrir nuevas experiencias auditivas!", musicfreak);
    Publicacion pub2 = new Publicacion(2, "Cómo comenzar tu propia colección de vinilos", "Sumérgete en el mundo de la música analógica con vinilos.", "¿Te gustaría comenzar tu propia colección de vinilos pero no sabes por dónde empezar? En este artículo, te guiaremos a través de los pasos para iniciar y construir una colección de vinilos que refleje tu pasión por la música.", musicfreak);
    Publicacion pub3 = new Publicacion(3, "Entrevista exclusiva: Conversando con una leyenda del jazz", "Una mirada íntima a la vida y carrera de un ícono del jazz.", "En esta entrevista exclusiva, hablamos con una leyenda viva del jazz sobre su carrera, influencias musicales y su visión del futuro del género. ¡Una lectura obligada para todos los amantes del jazz!", musicfreak);
    Publicacion pub4 = new Publicacion(4, "Los festivales de música más emblemáticos del mundo", "Explora los eventos musicales que no te puedes perder.", "Desde Coachella hasta Glastonbury, el mundo está lleno de festivales de música increíbles. En esta lista, destacamos algunos de los festivales más emblemáticos que debes experimentar al menos una vez en la vida.", musicfreak);
    Publicacion pub5 = new Publicacion(5, "Cómo escribir tu primera canción: Consejos para principiantes", "Desata tu creatividad y da vida a tus propias composiciones.", "¿Siempre has querido escribir tu propia música pero no sabes por dónde empezar? En este artículo, te ofrecemos consejos prácticos y técnicas para comenzar a componer tus propias canciones, ¡incluso si eres un principiante!", musicfreak);
    Publicacion pub6 = new Publicacion(6, "Explorando la cocina tailandesa: Sabores exóticos y auténticos", "Sumérgete en la rica tradición culinaria de Tailandia.", "En este artículo, te llevaremos en un viaje culinario a través de Tailandia, explorando los ingredientes, técnicas y platos más emblemáticos de esta fascinante cocina. Desde el curry hasta el pad thai, ¡prepárate para despertar tus papilas gustativas!", foodiegirl);
    Publicacion pub7 = new Publicacion(7, "Los mejores restaurantes de cocina de autor en tu ciudad", "Descubre los destinos gastronómicos que están redefiniendo la alta cocina.", "Si eres un amante de la gastronomía y buscas experiencias culinarias excepcionales, no te pierdas esta lista de los mejores restaurantes de cocina de autor en tu ciudad. ¡Prepárate para una experiencia gastronómica única e inolvidable!", foodiegirl);
    Publicacion pub8 = new Publicacion(8, "Cómo organizar una cena temática en casa: Ideas y consejos", "Crea una experiencia gastronómica memorable para tus invitados.", "¿Quieres impresionar a tus amigos y familiares con una cena temática en casa? En este artículo, te ofrecemos ideas creativas y consejos prácticos para organizar una cena temática exitosa que sorprenderá y deleitará a tus invitados.", foodiegirl);
    Publicacion pub9 = new Publicacion(9, "Descubriendo los encantos ocultos de Kyoto", "Explora la magia y la historia de la antigua capital de Japón.", "Sumérgete en la atmósfera única de Kyoto mientras descubres sus templos ancestrales, jardines zen y delicias culinarias. Desde el bullicioso mercado Nishiki hasta el tranquilo sendero del Filósofo, esta ciudad tiene algo para cada tipo de viajero.", travelbug22);
    Publicacion pub10 = new Publicacion(10, "Recorriendo la Ruta 66: Aventura en la carretera icónica de Estados Unidos", "Embárcate en un viaje épico a través del corazón de América.", "Desde Chicago hasta Santa Mónica, sigue el camino de la Ruta 66 y descubre la diversidad cultural y paisajes impresionantes que hacen de esta carretera una leyenda. Conoce los pueblos pintorescos, los sitios históricos y los restaurantes clásicos que hacen que este viaje sea inolvidable.", travelbug22);
    Publicacion pub11 = new Publicacion(11, "El renacimiento del arte callejero: Explorando las obras más impactantes", "Descubre la expresión artística en las calles de las ciudades más importantes del mundo.", "En esta publicación, exploramos cómo el arte callejero ha evolucionado de un acto clandestino a una forma de expresión respetada y apreciada. Desde los murales coloridos hasta las instalaciones efímeras, el arte callejero transforma paisajes urbanos y desafía percepciones.", artlover);
    Publicacion pub12 = new Publicacion(12, "Conversaciones con artistas emergentes: Descubriendo el talento oculto", "Sumérgete en el mundo de artistas emergentes y sus inspiraciones.", "En esta serie de entrevistas exclusivas, hablamos con artistas emergentes sobre su proceso creativo, inspiraciones y desafíos en el mundo del arte contemporáneo. Desde pintores hasta escultores, estas conversaciones revelan la diversidad y el talento de la escena artística actual.", artlover);
    Publicacion pub13 = new Publicacion(13, "El arte como terapia: Explorando los beneficios emocionales de la creación artística", "Descubre cómo el arte puede sanar y transformar vidas.", "En este artículo, exploramos el poder curativo del arte y cómo la creación artística puede ayudar a las personas a procesar emociones, reducir el estrés y encontrar significado en momentos difíciles. Desde la pintura hasta la cerámica, el arte ofrece un camino hacia la autoexpresión y la sanación.", artlover);
    Publicacion pub14 = new Publicacion(14, "Las galerías de arte más innovadoras del mundo: Destinos imprescindibles para los amantes del arte", "Explora espacios vanguardistas que desafían los límites del arte tradicional.", "Desde exposiciones multimedia hasta instalaciones interactivas, estas galerías de arte están redefiniendo la experiencia de ver y experimentar el arte. Descubre destinos emocionantes que presentan obras de artistas visionarios y despiertan nuevas perspectivas sobre el arte contemporáneo.", artlover);
    
    public List<Publicacion> publicaciones = Arrays.asList(pub1,pub2,pub3,pub4,pub5,pub6,pub7,pub8,pub9,pub10,pub11,pub12,pub13,pub14);
    
    public List<Comment> comentarios = Arrays.asList(
        new Comment(1, 1, foodiegirl, "Me parece una lista interesante, pero esperaba más variedad de géneros musicales.", 3),
        new Comment(2, 1, travelbug22, "¡Estoy emocionado de escuchar estos álbumes! Gracias por la recomendación.", 4),
        new Comment(3, 1, artlover, "No estoy de acuerdo, foodiegirl. Creo que la lista abarca una amplia gama de géneros y estilos musicales.", 4),
        new Comment(4, 1, fitnessfanatic, "No conozco la mayoría de estos álbumes, pero definitivamente los agregaré a mi lista de reproducción para el gimnasio.", 3),
        new Comment(5, 1, techgeek, "Estoy de acuerdo con travelbug22, la variedad de géneros es impresionante. ¡Es hora de actualizar mi biblioteca de música!", 5),
        new Comment(6, 1, artlover, "Estoy de acuerdo con techgeek. Esta lista tiene algo para todos los gustos musicales.", 5),
        new Comment(7, 1, techgeek, "fitnessfanatic, estoy seguro de que encontrarás varias canciones motivadoras en esta lista para tus entrenamientos.", 4),
        new Comment(8, 5, foodiegirl, "¡Interesante artículo! Aunque no soy músico, siempre he admirado el proceso creativo detrás de la composición musical. Este artículo me ha dado una visión fascinante de cómo comenzar en el mundo de la composición de canciones. ¡Gracias por compartir!", 5),
        new Comment(9, 5, travelbug22, "La música es un lenguaje universal y la composición musical es una forma hermosa de expresión. Aunque soy más un viajero que un músico, este artículo me ha inspirado para explorar mi creatividad de una manera diferente. ¡Gracias por la inspiración!", 4),
        new Comment(10, 5, artlover, "Como amante del arte en todas sus formas, siempre he sentido curiosidad por la composición musical. Este artículo me ha brindado una visión fascinante sobre cómo dar los primeros pasos en este arte. ¡Es hora de sacar mi lado creativo! Gracias por compartir tus conocimientos.", 5),
        new Comment(11, 8, artlover, "¡Qué idea tan emocionante! Siempre he querido organizar una cena temática en casa, pero nunca supe por dónde empezar. Este artículo definitivamente me dará la inspiración y la orientación que necesito. ¡Gracias!", 5),
        new Comment(12, 8, fitnessfanatic, "¡Esta publicación es perfecta para mí! Me encanta organizar cenas saludables para mis amigos y familiares, y estoy seguro de que estos consejos me ayudarán a hacerlo de una manera aún más creativa. ¡No puedo esperar para probar algunas de estas ideas!", 4),
        new Comment(13, 12, techgeek, "¡Qué fascinante serie de entrevistas! Como alguien que aprecia el arte en todas sus formas, estoy emocionado de sumergirme en el mundo de estos artistas emergentes y aprender más sobre sus inspiraciones y procesos creativos.", 5),
        new Comment(14, 12, travelbug22, "Me encanta la idea de explorar el talento emergente en el mundo del arte. Siempre es emocionante descubrir nuevos artistas y ver cómo están redefiniendo la escena artística contemporánea.", 4),
        new Comment(15, 12, foodiegirl, "¡Qué concepto tan interesante! Como amante del arte y la cultura, estoy emocionado de leer estas entrevistas y descubrir nuevos talentos en el mundo del arte contemporáneo.", 4),
        new Comment(16, 12, artlover, "¡Estoy completamente de acuerdo! Esta publicación promete ser una fuente de inspiración para cualquiera que esté interesado en el arte y en descubrir nuevos talentos emergentes. No puedo esperar para sumergirme en estas conversaciones.", 5)
    );

}
