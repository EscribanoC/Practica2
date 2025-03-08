// Agregar el evento a todos los botones con la clase "miBoton"
const botones = document.querySelectorAll('.btnEliminarActividad');

botones.forEach(boton => {
    boton.addEventListener('click', function (event) {

        // Prevenir el comportamiento por defecto del formulario (evitar submit)
        event.preventDefault();

        if (confirm("¿Está seguro de eliminar la actividad con sus imágenes?")) {
            // Obtener el ID de la actividad desde el atributo 'data-id' del botón
            const idActividad = boton.getAttribute('id');
            let url = "ControladorEliminarActividad?idActividad=" + idActividad;
            location.href = url;
        }
    });

});
