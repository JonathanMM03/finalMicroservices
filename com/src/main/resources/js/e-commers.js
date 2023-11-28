/**
 * Clase Producto para representar un producto.
 * @class
 * @param {number} id - Identificador único del producto.
 * @param {string} nombre - Nombre del producto.
 * @param {string} descripcion - Descripción del producto.
 * @param {number} costo - Costo del producto.
 */
class Producto {
    constructor(id, nombre, descripcion, costo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }
}


/**
 * Función para guardar un nuevo producto.
 * @async
 * @function
 */
async function guardarProducto() {
    try {
        // Obtener valores del formulario
        const nombre = document.getElementById('nombre').value;
        const descripcion = document.getElementById('descripcion').value;
        const precio = document.getElementById('precio').value;

        // Validar que se ingresen valores válidos
        if (!nombre || !descripcion || !precio) {
            throw new Error('Todos los campos son obligatorios.');
        }

        // Crear el objeto Producto
        const nuevoProducto = new Producto(null, nombre, descripcion, parseFloat(precio));

        // Enviar la solicitud POST para agregar el producto
        const response = await fetch('http://localhost:8080/productos/agregar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(nuevoProducto),
        });

        if (response.ok) {
            // Limpiar el formulario después de guardar
            document.getElementById('productoForm').reset();

            // Recargar las tarjetas de productos
            await cargarTarjetasProductos();

            // Mostrar mensaje de éxito
            iziToast.success({ title: 'Éxito', message: 'Producto agregado exitosamente.', icon: 'icon-check' });
        } else {
            // Manejar errores de la respuesta
            const errorMessage = await response.text();
            throw new Error(`Error al agregar el producto: ${errorMessage}`);
        }
    } catch (error) {
        // Manejar errores generales
        console.error('Error al guardar el producto:', error);
        iziToast.error({ title: 'Error', message: error.message, icon: 'icon-close' });
    }
}


/**
 * Función para cargar la tabla de productos.
 * @async
 * @function
 */
async function cargarTarjetasProductos() {
    try {
        // Obtener la lista de productos ordenados por precio
        const response = await fetch('http://localhost:8080/productos/getAllSortedByPrice');
        const productos = await response.json();
        const tarjetasProductos = document.getElementById('tarjetasProductos');
        tarjetasProductos.innerHTML = '';

        // Crear tarjetas para cada producto y agregarlas al contenedor
        productos.forEach(producto => {
            const tarjeta = `
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${producto.nombre}</h5>
                            <p class="card-text">Descripción: ${producto.descripcion}</p>
                            <p class="card-text">Costo: $${producto.costo}</p>
                            <button class="btn btn-warning btn-sm" onclick="editarProducto(${producto.id})">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${producto.id})">Eliminar</button>
                        </div>
                    </div>
                </div>
            `;

            tarjetasProductos.innerHTML += tarjeta;
        });
    } catch (error) {
        console.error('Error al cargar las tarjetas de productos:', error);
    }
}


/**
 * Función para llenar el carrusel con los 3 productos más caros.
 * @async
 * @function
 */
async function llenarCarrusel() {
    try {
        // Obtener los 3 productos más caros
        const response = await fetch('http://localhost:8080/productos/getTop3Expensive');
        const top3Productos = await response.json();
        const carruselProductos = document.getElementById('carruselProductos');
        carruselProductos.innerHTML = '';

        // Crear elementos de carrusel para cada producto y agregarlos al carrusel
        top3Productos.forEach((producto, index) => {
            const card = document.createElement('div');
            card.classList.add('carousel-item');
            if (index === 0) {
                card.classList.add('active'); // La primera tarjeta está activa inicialmente
            }
            card.innerHTML = `
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${producto.nombre}</h5>
                        <p class="position-absolute">${index + 1}º</p>
                    </div>
                </div>
            `;
            carruselProductos.appendChild(card);
        });
    } catch (error) {
        console.error('Error al llenar el carrusel:', error);
    }
}


/**
 * Función para iniciar el carrusel.
 * @async
 * @function
 */
async function iniciarCarrusel() {
    try {
        // Llenar el carrusel con los 3 productos más caros
        await llenarCarrusel();

        // Configurar el carrusel
        $('#carruselProductos').carousel({
            interval: 2000, // Cambia cada 2 segundos
            wrap: true // Permite moverse hacia atrás después de llegar al final
        });
    } catch (error) {
        console.error('Error al iniciar el carrusel:', error);
    }
}

// Cargar las tarjetas de productos y luego iniciar el carrusel al cargar la página
document.addEventListener('DOMContentLoaded', async () => {
    await cargarTarjetasProductos();
    await iniciarCarrusel();
});