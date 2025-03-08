const actividades = document.querySelectorAll(".actividad")

actividades.forEach(actividad => {
    actividad.addEventListener("click", async function (event) {
        // Obtener el ID de la actividad desde el atributo 'data-id' del bot�n
        const idActividad = actividad.getAttribute('id');
        try {
            let url = "ControladorEditarActividad?idActividad=" + idActividad;

            const res = await fetch(url)
            if (!res.ok) { //Si la respuesta no es correcta
                throw new Error(`Error en la solicitud: ${res.statusText}`);
            }

            let actividad = await res.json();//Parsea la respuesta a JSON

            //console.log(actividad)
            document.querySelector(".formularioEditarActividad").action = "ControladorEditarActividad";
            document.querySelector("#idActividad").value = actividad.id;
            document.querySelector("#tituloActividad").value = actividad.titulo;
            document.querySelector("#fechaActividad").value = actividad.fecha;
            document.querySelector("#descripcionActividad").value = actividad.descripcion;
            if (actividad.imagen !== "null") {//Si la actividad no tiene imagen
                document.querySelector(".imagenEnMiniatura").style.display = "block";
                document.querySelector("#imagenActividad").src = "./media/" + actividad.imagen;
            } else {
                document.querySelector(".imagenEnMiniatura").style.display = "none";
                document.querySelector("#imagenActividad").src = "";
            }
            
            //Comprueba que se haya pulsado SOLO en la parte de la actividad y no en el bot�n de borrar
            if (event.target.matches('.actividad') && !event.target.matches('.btnEliminarActividad')) {
                //Muestra la modal
                document.getElementById('modal').style.display = 'flex';
            }
        } catch (error) {
            console.error("ERROR EN LA PETICI�N: " + error);
        }

    });

});