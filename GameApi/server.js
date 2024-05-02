const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();

app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', '*');
    next();
});

app.use(bodyParser.json());

const PUERTO = 8080;

const conexion = mysql.createConnection(
    {
        host: 'localhost',
        database: 'juego',
        user: 'root',
        password: ''
    }
);

app.listen(PUERTO, () => {
    console.log(`Servidor corriendo en el puerto ${PUERTO}`);
});

conexion.connect(error => {
    if (error) throw error;
    console.log('Conexión exitosa a la base de datos');
});

let obj = {};

app.get('/', (req, res) => {
    res.send('API');
});

// Ruta para obtener todos los resultados
app.get('/result', (req, res) => {
    const query = `SELECT * FROM resultados;`;
    conexion.query(query, (error, resultado) => {
        if (error) return console.error(error.message);

        if (resultado.length > 0) {
            obj.codigo = "200";
            obj.mensaje = "Lista de Resultados";
            obj.data = resultado;
            res.json(obj);
        } else {
            obj.codigo = "400";
            obj.mensaje = "No hay registros de resultados";
            obj.data = [];
            res.json(obj);
        }
    });
});

// Ruta para obtener un resultado por ID
app.get('/result/:id', (req, res) => {
    const { id } = req.params;
    const query = `SELECT * FROM resultados WHERE id_resultado=${id};`;
    conexion.query(query, (error, resultado) => {
        if (error) return console.error(error.message);

        if (resultado.length > 0) {
            obj.codigo = "200";
            obj.mensaje = "Resultado";
            obj.data = resultado;
            res.json(obj);
        } else {
            obj.codigo = "400";
            obj.mensaje = "No hay registro de resultado con ese ID";
            obj.data = [];
            res.json(obj);
        }
    });
});

// Ruta para agregar un resultado
app.post('/result', (req, res) => {
    const resultado = {
        nombre_partida: req.body.nombre_partida,
        nombre_jugador1: req.body.nombre_jugador1,
        nombre_jugador2: req.body.nombre_jugador2,
        ganador: req.body.ganador,
        punto: req.body.punto,
        estado: req.body.estado
    };

    const query = 'INSERT INTO resultados SET ?';

    conexion.query(query, resultado, (error) => {
        if (error) {
            console.error(error.message);
            obj.codigo = "500";
            obj.mensaje = "Error al agregar el resultado";
            obj.data = [];
            res.json(obj);
        } else {
            obj.codigo = "200";
            obj.mensaje = "Se insertó correctamente el resultado";
            obj.data = [];
            res.json(obj);
        }
    });
});

// Ruta para actualizar un resultado por ID
app.put('/result/:id', (req, res) => {
    const { id } = req.params;
    const {
        nombre_partida,
        nombre_jugador1,
        nombre_jugador2,
        ganador,
        punto,
        estado
    } = req.body;

    const query = `UPDATE resultados SET
        nombre_partida='${nombre_partida}',
        nombre_jugador1='${nombre_jugador1}',
        nombre_jugador2='${nombre_jugador2}',
        ganador='${ganador}',
        punto='${punto}',
        estado='${estado}'
        WHERE id_resultado=${id};`;

    conexion.query(query, (error) => {
        if (error) {
            console.error(error.message);
            obj.codigo = "500";
            obj.mensaje = "Error al actualizar el resultado";
            obj.data = [];
            res.json(obj);
        } else {
            obj.codigo = "200";
            obj.mensaje = "Se actualizó correctamente el resultado";
            obj.data = [];
            res.json(obj);
        }
    });
});

// Ruta para eliminar un resultado por ID
app.delete('/result/:id', (req, res) => {
    const { id } = req.params;

    const query = `DELETE FROM resultados WHERE id_resultado=${id};`;
    conexion.query(query, (error) => {
        if (error) {
            console.error(error.message);
            obj.codigo = "500";
            obj.mensaje = "Error al eliminar el resultado";
            obj.data = [];
            res.json(obj);
        } else {
            obj.codigo = "200";
            obj.mensaje = "Se eliminó correctamente el resultado";
            obj.data = [];
            res.json(obj);
        }
    });
});