 function generaCategoria(descripcion, estaSeleccionada) {
     var classCategoria = `categoria${estaSeleccionada?"-seleccionada":""} fw-700`;
     var categoria = document.createElement('div');
     var contenido = document.createElement('p');

     contenido.innerHTML = descripcion;
     categoria.className = classCategoria;

     categoria.appendChild(contenido);
     categoria.setAttribute("id", descripcion);

     return categoria;
 }


 export default generaCategoria;