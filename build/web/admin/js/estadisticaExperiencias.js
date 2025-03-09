async function cargarGraficaExperiencias() {
    let fecha1 = document.querySelector("#fecha1").value
    let fecha2 = document.querySelector("#fecha2").value
//    console.log(fecha1)
//    if (fecha1 === "" || fecha2 === "") {
//        fecha1 = "2025-01-01"
//        
//        const today = new Date();
//
//        // Obtener el año, mes y día
//        const year = "" + today.getFullYear();
//        const month = (today.getMonth() + 1).toString().padStart(2, '0'); // Los meses comienzan desde 0
//        const day = today.getDate().toString().padStart(2, '0');
//        console.log(year)
//        console.log(month)
//        console.log(day)
//        // Formatear la fecha como yyyy/MM/dd
//        const fecha2 = `${year}-${month}-${day}`;
//        console.log(fecha2)
//    }

    const res = await fetch('ControladorCargarGraficaExperiencias?fecha1=' + fecha1 + '&fecha2=' + fecha2)
    let experiencias = await res.json();

    //Creo el array bidimensional
    let arrayExperiencias = []

    for (let experiencia in experiencias) {
        // Añadimos una fila con la experiencia y el número de actividades
        arrayExperiencias.push(["" + experiencia, experiencias[experiencia]]);
    }

    //Ordena el array bidimensional por número de experiencias de forma decreciente
    arrayExperiencias.sort((a, b) => b[1] - a[1])


    Highcharts.chart('container2', {
        chart: {
            type: 'column'
        },
        title: "",
        xAxis: {
            type: 'category',
            labels: {
                autoRotation: [-45, -90],
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Número de actividades'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: 'Número total de actividades: <b>{point.y:.1f}</b>'
        },
        series: [{
                name: 'Population',
                colors: [
                    '#9b20d9', '#9215ac', '#861ec9', '#7a17e6', '#7010f9', '#691af3',
                    '#6225ed', '#5b30e7', '#533be1', '#4c46db', '#4551d5', '#3e5ccf',
                    '#3667c9', '#2f72c3', '#277dbd', '#1f88b7', '#1693b1', '#0a9eaa',
                    '#03c69b', '#00f194'
                ],
                colorByPoint: true,
                groupPadding: 0,
                data: arrayExperiencias,
                dataLabels: {
                    enabled: true,
                    rotation: -90,
                    color: '#FFFFFF',
                    inside: true,
                    verticalAlign: 'top',
                    y: 10, // 10 pixels down from the top
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }]
    });

}

cargarGraficaExperiencias();