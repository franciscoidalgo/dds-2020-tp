 function generaCategoria(descripcion, estaSeleccionada) {
     let classCategoria = `categoria${estaSeleccionada?"-seleccionada":""} fw-700`;
     let categoria = document.createElement('div');
     let contenido = document.createElement('p');

     contenido.innerHTML = descripcion;
     categoria.className = classCategoria;
     categoria.id=descripcion;

     categoria.appendChild(contenido);
     categoria.setAttribute("id", descripcion);
     return categoria;
 }



 export default generaCategoria;