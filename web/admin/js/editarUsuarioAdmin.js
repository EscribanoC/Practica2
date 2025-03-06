const modal = document.querySelector(".modal");
function editarUsuario(idUsuario) {
    document.getElementById('modal').style.display = 'flex'; //Muestra el modal de edición de usuario

    //Petición al servidor que recibirá los datos del usuario a editar y los cargará en la vista
    fetch("ControladorEditarUsuario?idUsuario=" + idUsuario, {
        method: 'GET'})
            .then(function (response) {
                if (response.ok) {//Si la solicitud ha ido bien
                    return response.json();
                } else {//Si hay un error en la solicitud
                    throw new Error("Error en la solicitud");
                }
            })
            .then(data => {//Carga los campos del usuario
                document.getElementById('emailOriginal').value = data.email;
                document.getElementById('emailUsuario').value = data.email;
                document.getElementById('nombreUsuario').value = data.nombre;
                document.getElementById('apellidosUsuario').value = data.apellidos;
            })
            .catch((error) => {//Si hay un error en la petición
                console.error('Error: ' + error);
            });
}

window.onclick = function (event) {//Si se clicka fuera de la ventana modal cerrará esta
    if (event.target === modal) {
        modal.style.display = "none";
    }
};