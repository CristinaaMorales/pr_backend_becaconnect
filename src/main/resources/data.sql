-- Insertar 5 Posts creados por el admin (id=1)
INSERT INTO post (id, title, content, posted_by, img, date, like_count, view_count, comment_count)
VALUES
    (2, 'Becas de intercambio y apoyos institucionales de la Unión Europea',
     'La Unión Europea (UE) lanzó la convocatoria 2024 del Programa Erasmus+, que ofrece oportunidades educativas a peruanos en diversos países europeos, informó el Programa Nacional de Becas y Crédito Educativo (Pronabec) del Ministerio de Educación del Estado peruano. Entre los beneficios académicos de este programa, se incluye la movilidad de los estudiantes, formación de docentes, voluntariados de jóvenes, así como apoyo para el reforzamiento educativo de instituciones educativas.
Erasmus fue fundado en 1987 y se ha convertido en el programa insignia de la UE para la educación. Tiene el objetivo de facilitar el intercambio académico y cultural de ciudadanos de todo el mundo, consolidando, de esta forma, las relaciones entre los países participantes.
La convocatoria 2024 trae una variedad de propuestas dirigidas a diferentes públicos, por lo que varían los beneficios, procesos y plazos de postulación:',
     'admin',
     'https://cdn.www.gob.pe/uploads/campaign/photo/000/068/408/union-europea-bandera.png',
     NOW(), 10, 85, 0),

    (3, 'Becas de Posgrado en Estados Unidos',
     '¡Atento a esta nueva oportunidad para estudiar en el extranjero! Si eres un profesional peruano de alto rendimiento académico, con buen conocimiento del idioma inglés y habilidades de liderazgo, puedes postular a los diferentes programas de las becas Fulbright para estudiar un posgrado en Estados Unidos.

Además, si tienes dudas en los procesos de postulación, la Comisión Fulbright ofrece los servicios del Centro de Asesoría Educativa, gracias a los cuales puedes obtener orientación detallada.

Para esta convocatoria 2024 son cuatro programas a los que ya puedes aplicar:
',
     'admin',
     'https://cdn.www.gob.pe/uploads/document/file/6020922/917999-docentes-del-nivel-secundaria-pueden-acceder-a-una-beca-de-fulbright-2.jpg',
     NOW(), 25, 120, 0),

    (4, 'Cómo preparar tu carta de motivación',
     ' Cuando te preguntes cómo hacer una carta de motivación, lo primero que deberías tener en cuenta es que se trata de un documento orientado a captar la atención del lector y convencerlo. Para ello, deberás justificar, tanto en el plano personal como profesional, qué es lo que te lleva a presentarte a esa plaza, beca, puesto de trabajo, etc. Su objetivo es demostrar que tienes un interés real, tu recorrido es afín a lo que se busca y sabes cómo argumentarlo.

Antes de empezar a escribir, debes tener bien claro los motivos. Anota todo lo que crees que debería estar en tu carta de motivación. Considera puntos clave como logros académicos, aspiraciones, metas y, sobre todo, las razones por las que quieres optar a ese programa o trabajo. ',
     'admin',
     'https://www.santanderopenacademy.com/es/blog/como-hacer-una-carta-de-motivacion/_jcr_content/root/hero/imageDesktop.coreimg.jpeg/1689587536740/como-hacer-carta-de-motivacion.jpeg',
     NOW(), 8, 50, 0),

    (5, 'Planificación para estudiar en Japón',
     'Las becas no están concebidas para sufragar todos los gastos necesarios para cursar estudios internacionales, sino que la mayoría se destinan a sufragar parcialmente los gastos de manutención, matrículación y otros gastos importantes. Por ello, es importante realizar cálculos detallados de los costes totales previstos de los estudios en el extranjero y, a continuación, elaborar planes económicos fiables que no dependan únicamente de las becas, sino que incluyan también los propios recursos económicos personales.',
     'admin',
     'https://images-intl.prod.aws.idp-connect.com/hca-cont/img/images/schr_img.webp',
     NOW(), 12, 75, 0),

    (6, 'Minedu ofrece 150 becas integrales de posgrado para estudiar en las mejores universidades del mundo',
     'El Ministerio de Educación (Minedu) puso a disposición de los profesionales del país 150 becas integrales de posgrado que les permitirá estudiar en una de las mejores universidades del mundo. Se trata de la Beca Generación del Bicentenario 2024.
A través del Programa de Becas y Crédito Educativo (Pronabec), el Minedu anunció que la convocatoria concluirá el próximo 1 de julio y que profesionales con insuficientes recursos económicos podrán postular a 135 becas para estudios de maestrías y 15 para doctorados.
El ministro de Educación, Morgan Quero, indicó que para postular se debe contar, entre otros requisitos, con la carta de aceptación definitiva de una institución de educación superior ubicada entre las 400 primeras del mundo, tener nacionalidad peruana y pertenecer como mínimo al tercio superior o equivalente.
Agregó que la postulación es virtual y gratuita, a través de www.pronabec.gob.pe/beca-generacion-bicentenario/, y entre los beneficios que obtendrán los ganadores se considera la cobertura de todos sus gastos académicos y de manutención. La beca también incluye la matrícula y pensión de estudios, los gastos administrativos para la obtención del grado respectivo, alojamiento, alimentación, entre otros.',
     'admin',
     'https://cdn.www.gob.pe/uploads/document/file/6383418/959347-whatsapp-image-2024-05-22-at-10-48-30.jpeg',
     NOW(), 20, 95, 0);


INSERT INTO post_tags (post_id, tag)
VALUES
    (2, 'becas'),
    (2, 'Europa'),
    (3, 'posgrado'),
    (5, 'Asia'),
    (6, 'online');
