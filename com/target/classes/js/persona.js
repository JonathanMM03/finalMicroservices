/* global Swal, iziToast */

// Función para cargar la tabla de personas
async function cargarTablaPersonas() {
    try {
        const response = await fetch('http://localhost:8080/personas/listarPersonas');
        const personas = await response.json();
        const tablaPersonas = document.getElementById('tablaPersonas');
        tablaPersonas.innerHTML = '';

        personas.forEach(persona => {
            const fila = `
                <tr>
                    <td>${persona.id}</td>
                    <td>${persona.nombre}</td>
                    <td>${persona.edad}</td>
                    <td>${persona.email}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editarPersona(${persona.id})">Editar</button>
                        <button class="btn btn-danger btn-sm" onclick="eliminarPersona(${persona.id})">Eliminar</button>
                    </td>
                </tr>
            `;
            tablaPersonas.innerHTML += fila;
        });
    } catch (error) {
        console.error('Error al cargar la tabla de personas:', error);
    }
}

// Función para guardar o actualizar una persona
async function guardarPersona() {
    const personaId = document.getElementById('personaId').value;
    const nombre = document.getElementById('nombre').value;
    const edad = document.getElementById('edad').value;
    const email = document.getElementById('email').value;

    const persona = { nombre, edad, email };

    try {
        if (personaId) {
            // Si hay un ID, estamos actualizando una persona existente
            await actualizarPersona(personaId, persona);
        } else {
            // Si no hay un ID, estamos creando una nueva persona
            await crearPersona(persona);
        }

        // Limpiar el formulario después de guardar/actualizar
        document.getElementById('personaForm').reset();

        // Recargar la tabla de personas
        await cargarTablaPersonas();

        iziToast.success({ title: 'Éxito', message: 'Persona guardada exitosamente.', icon: 'icon-check' });
    } catch (error) {
        console.error('Error al guardar/actualizar la persona:', error);
        iziToast.error({ title: 'Error', message: 'Hubo un error al guardar/actualizar la persona.', icon: 'icon-close' });
    }
}

// Función para editar una persona
async function editarPersona(id) {
    try {
        const response = await fetch(`http://localhost:8080/personas/${id}`);
        const persona = await response.json();

        // Llenar el formulario con los datos de la persona
        document.getElementById('personaId').value = persona.id;
        document.getElementById('nombre').value = persona.nombre;
        document.getElementById('edad').value = persona.edad;
        document.getElementById('email').value = persona.email;
    } catch (error) {
        console.error('Error al obtener los datos de la persona para editar:', error);
    }
}

// Función para eliminar una persona
async function eliminarPersona(id) {
    try {
        const confirmacion = await Swal.fire({
            title: '¿Estás seguro?',
            text: 'Esta acción no se puede deshacer.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Sí, eliminar'
        });

        if (confirmacion.isConfirmed) {
            await fetch(`http://localhost:8080/personas/${id}`, { method: 'DELETE' });

            // Recargar la tabla de personas después de la eliminación
            await cargarTablaPersonas();

            iziToast.success({ title: 'Éxito', message: 'Persona eliminada exitosamente.', icon: 'icon-check' });
        }
    } catch (error) {
        console.error('Error al eliminar la persona:', error);
        iziToast.error({ title: 'Error', message: 'Hubo un error al eliminar la persona.', icon: 'icon-close' });
    }
}

// Función para crear una nueva persona
async function crearPersona(persona) {
    const response = await fetch('http://localhost:8080/personas/crearPersona', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(persona),
    });

    const nuevaPersona = await response.json();
    return nuevaPersona;
}

// Función para actualizar una persona existente
async function actualizarPersona(id, persona) {
    const response = await fetch(`http://localhost:8080/personas/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(persona),
    });

    const personaActualizada = await response.json();
    return personaActualizada;
}

// Cargar la tabla de personas al cargar la página
document.addEventListener('DOMContentLoaded', async () => {
    await cargarTablaPersonas();
});
