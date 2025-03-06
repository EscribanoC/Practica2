function filtrarExperiencias() {
    filtro = document.getElementById("filtroBusqueda").value;
    
    //Permite editar la codificación de la respuesta en la petición fetch
    var cabecera = new Headers();
    cabecera.append('Content-Type', 'text/plain; charset=ISO-8859-1');

    fetch("ControladorFiltroExperiencias?filtro=" + filtro, cabecera)
            .then(function (response) {
                return response.arrayBuffer();
            })
            .then(data => {
                const decoder = new TextDecoder('ISO-8859-1');
                data = decoder.decode(data);
                document.querySelector("#listaExperiencias").innerHTML = data;
            })
            .catch((error) => {
                console.error('Error: ' + error);
            });
}