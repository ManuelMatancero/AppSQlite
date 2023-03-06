# AppSQlite
# Descripcion
La siguiente aplicacion es una herramienta para agregar, visualizar, editar y eliminar notas,
esta aplicacion ofrece una gran comodidad a la hora de guardar textos importantes para ser recordados
una vez abierta la aplicacion.

# Funcionamiento
La aplicacion consta de 3 Actividades principales, la actividad de inicio donde se muestra un titulo
que dice Notas, un subtitulo que dice Toma todo en tus manos, una lista donde se desplegaran las notas
a medida que estas se agreguen y un boton con un simbolo + para que al pulsarlo se envie a la actividad 
correspondiente de agregar notas; la actividad de agregar notas contiene 2 campos de edit text uno para
agregar el titulo y otro para agregar la descripcion de la nota, tambien tiene un boton para agregar una
imagen a la nota y 1 boton para guardar la nota y otro para cancelar la operacion y volver a la actividad 
principal. Por ultimo tiene una actividad Editar notas donde se presentaran las informaciones a editar de la
nota seleccionada con el mismo diseño que la actividad para agregar notas.

# Funcionalidad de SQlite
En esta applicacion uticice la Libreria de Persistencia Room la cual es una biblioteca de Android que 
proporciona una capa de abstracción sobre SQLite para permitir un acceso más fácil y eficiente a la base 
de datos en aplicaciones Android. Aqui cree una base de datos llamada nota con los campos id, titulo, descripcion e imagen
y realice el proceso necesario para crear los metodos Listar, insertar, editar, buscarPorId y 
eliminar, con estos metodos le di la funcionalidad general a la aplicacion.