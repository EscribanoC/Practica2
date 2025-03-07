async function cargarGraficaUsuarios() {

    const res = await fetch('ControladorCargarGraficaUsuarios')
    let usuarios = await res.json();

    //Creo el array bidimensional
    let arrayExperiencias = []

    for (let email in usuarios) {
        // Añadimos una fila con el email y el número de experiencias
        arrayExperiencias.push(["" + email, usuarios[email]]);
    }
    
    //Ordena el array bidimensional por número de experiencias de forma decreciente
    arrayExperiencias.sort((a, b) => b[1] - a[1])


    Highcharts.chart('container', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Número total de experiencias por Usuario'
        },
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
                text: 'Número de experiencias'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: 'Número total de experiencias: <b>{point.y:.1f}</b>'
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
cargarGraficaUsuarios();