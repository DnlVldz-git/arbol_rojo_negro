public class main { 
    public static void main(String[] args) {
       
        arbol  arbolito = new arbol();
        
        System.out.println("A R B O L   N E G R O - R O J O\n");
        System.out.println("Se van a agregar 20 elementos aleatorios al arbol negro-rojo\n");
        System.out.println("\nLos cuales son:\n");
        
        for(int i = 0; i < 20; ++i){
            int dato = (int) (Math.random() * 100 + 1);
            arbolito.insertar(dato);
            System.out.print(dato+", ");
        }
    
        System.out.println("\n\n\n R E C O R R I D O S\n");
        System.out.println("---------------------------------------------------------------------------");
            
        System.out.println("\nEl recorrido In Orden sería:\n");
        arbolito.recorrer(0);
        
        System.out.println("\n\nEl recorrido Pre Orden sería:\n");
        arbolito.recorrer(1);     
        
        System.out.println("\n\nEl recorrido Post Orden sería:\n");
        arbolito.recorrer(2);
        
        System.out.println("\n\n\n\nB U S Q U E D A\n");
        System.out.println("---------------------------------------------------------------------------");
        
        boolean encontrado = false;
        do{
            int buscar = (int) (Math.random() * 100 + 1);
            System.out.println("\n\nSe buscará el número aleatorio : "+buscar+"\n");
            encontrado = arbolito.buscar(buscar);
        }while(encontrado == false);
        
        System.out.println("\n\n\n E L I M I N A C I O N\n");
        System.out.println("---------------------------------------------------------------------------");
        
        boolean eliminado = false;
        do{
            int eliminar = (int) (Math.random() * 100 + 1);
            System.out.println("\n\nSe eliminará el número aleatorio : "+eliminar+"\n");
            eliminado = arbolito.eliminar(eliminar);
        }while(eliminado == false);
        System.out.println("\nÁrbol actualizado\n");
        arbolito.recorrer(0);
        
    }
    
}
class arbol{
    nodo raiz = null; 
    nodo nulo = nulo(); //se crea la entidad nulo que servirá como null
    
    public nodo nulo(){ //método para crear el nulo, el cual será negro.
        nodo nulito = new nodo();
        nulito.izq = null;
        nulito.der = null;
        nulito.color = 'b';
        raiz = nulito;
        
        return nulito;
    }
    
    public void insertar(int dato){ //método para insertar nuevo elemento
        nodo nuevo = crear_nodo(dato); //se crea el nodo 
        nodo y = null;
        nodo x = this.raiz;

        while (x != nulo) { //se buscará la posición del nodo
            y = x;
            if (nuevo.dato < x.dato){
                x = x.izq;
            }else{
                x = x.der;
            }
        }
        nuevo.padre = y;
        if (y == null) { //se definirá si es hijo izquierdo o derecho o si el árbol está vacío
            raiz = nuevo;
        }else if(nuevo.dato < y.dato){
            y.izq = nuevo;
        }else{
            y.der = nuevo;
        }

        if (nuevo.padre == null) { //se checará si el árbol es de un elemento o no 
            nuevo.color = 'b';
        }else if (nuevo.padre.padre == null){
        }else{
           arreglar_insertar(nuevo); //se llama al función arreglar insertar
        }
    }
    
    boolean bandera = false;
    
    public boolean eliminar(int dato){ //el m´étodo para eliminar un nodo
        nodo nodo_a_eliminar =  encontrar(raiz, dato); //primero checará que el elemento exista
        if(nodo_a_eliminar != null){ 
            bandera = true;
            nodo aux = new nodo(); //se van a necesitar dos auxiliares
            nodo aux2 = new nodo();
            
            aux2 = nodo_a_eliminar; 
            char aux2color = aux2.color;
            
            if(nodo_a_eliminar.izq == nulo){ 
                aux = nodo_a_eliminar.der;
                intercambio(nodo_a_eliminar, nodo_a_eliminar.der);
            }else if(nodo_a_eliminar.der == nulo){
                aux = nodo_a_eliminar.izq;
                intercambio(nodo_a_eliminar, nodo_a_eliminar.izq);
            }else{
                aux2 = minimo(nodo_a_eliminar.der);
                aux2color = aux2.color;
                aux = aux2.der;
                if(aux2.padre == nodo_a_eliminar){
                    aux.padre = aux2;
                }else{
                    intercambio(aux2, aux2.der);
                    aux2.der = nodo_a_eliminar.der;
                    aux2.der.padre = aux2;
                }
                intercambio(nodo_a_eliminar, aux2);
                aux2.izq = nodo_a_eliminar.izq;
                aux2.izq.padre = aux2;
                aux2.color = nodo_a_eliminar.color;
            }
            System.out.print("\nSe eliminó el número " + dato + "\n");
            if(aux2color == 'b') arreglar_eliminar(aux);  
        }else{
            System.out.print("\nNo se eliminó el número porque no existe\n");
            bandera = false;
        }
        return bandera;
    }
    
    public nodo minimo(nodo aux){ //método que busca el elemento minimo del árbol
        while(aux.izq != nulo){
            aux = aux.izq;
        }
        return aux;
    }
    
    public void intercambio(nodo aux, nodo aux2){ //función que intercambia los apuntadores
        if(aux.padre == null){
            raiz = aux2;
        }else if(aux == aux.padre.izq){
            aux.padre.izq = aux2;
        }else{
            aux.padre.der = aux2;
        }
        aux2.padre = aux.padre;
    }
    
    public boolean buscar(int dato){ //función que busca el número dado
        nodo buscar = encontrar(raiz, dato);
        boolean bandera = false;
        if(buscar == null){
            System.out.println("No se encontró el número");
            bandera = false;
        }else{
            System.out.println("Se ha encontrado el número: "+dato);
            bandera = true;
        }
        return bandera;
    }
    
    public nodo encontrar(nodo aux, int dato){ //función que localiza el nodo con el dato dado
        if (aux == null || dato == aux.dato) return aux;
        if (dato < aux.dato) return encontrar(aux.izq, dato);
        return encontrar(aux.der, dato);
    }
    
    public void recorrer(int tipo){ //función que se encarga de multiplexar qué recorrido hará
        switch (tipo){
            case 0:
                in_orden(raiz);
                break;
            case 1:
                pre_orden(raiz); 
                break;
            case 2:
                post_orden(raiz);               
                break;
        }
    }
    
    public void in_orden(nodo aux){ //recorrido in_orden
        if (aux != nulo) {
            in_orden(aux.izq);
            System.out.print(aux.dato + " ");
            in_orden(aux.der);
        } 
    }
    
    public void post_orden(nodo aux){//recorrido post_orden
        if (aux != nulo) {
            post_orden(aux.izq);
            post_orden(aux.der);
            System.out.print(aux.dato + " ");
        } 
    }
    
    public void pre_orden(nodo aux){ //recorrido pre_orden
        if (aux != nulo) {
            System.out.print(aux.dato + " ");
            pre_orden(aux.izq);
            pre_orden(aux.der);
        } 
    }
    
    public void arreglar_insertar(nodo nuevo) { //función que arregla el árbol después de insertar un elemento
        nodo tio;
        while (nuevo.padre.color == 'r'){
            if (nuevo.padre == nuevo.padre.padre.der) {
                tio = nuevo.padre.padre.izq;
                if (tio.color == 'r') {
                    tio.color = 'b';
                    nuevo.padre.color = 'b';
                    nuevo.padre.padre.color = 'r';
                    nuevo = nuevo.padre.padre;
                }
                else{
                    if (nuevo == nuevo.padre.izq){
                        nuevo = nuevo.padre;
                        rotacion_derecha(nuevo);
                    }
                    nuevo.padre.color = 'b';
                    nuevo.padre.padre.color = 'r';
                    rotacion_izquierda(nuevo.padre.padre);
                }
            }
            else{ 
                tio = nuevo.padre.padre.der;
                if (tio.color == 'r'){
                    tio.color = 'b';
                    nuevo.padre.color = 'b';
                    nuevo.padre.padre.color = 'r';
                    nuevo = nuevo.padre.padre;
                }
                else{                   
                    if (nuevo == nuevo.padre.der) {
                        nuevo = nuevo.padre;
                        rotacion_izquierda(nuevo);
                    }
                    nuevo.padre.color = 'b';
                    nuevo.padre.padre.color = 'r';
                    rotacion_derecha(nuevo.padre.padre);
                }
            }
            if (nuevo == raiz) break;
        }
        raiz.color = 'b';
    }
    
    public void arreglar_eliminar(nodo nuevo){ //función que arregla el árbol después de eliminar un elemento
        nodo tio;
        while(nuevo != raiz && nuevo.color == 'b'){
            if(nuevo == nuevo.padre.izq){
                tio = nuevo.padre.der;
                
                if(tio.color == 'r'){
                    tio.color = 'b';
                    nuevo.padre.color = 'r';
                    rotacion_izquierda(nuevo.padre);
                    tio = nuevo.padre.der;
                }
                if(tio.izq.color == 'b' && tio.der.color == 'b'){
                    tio.color = 'r';
                    nuevo = nuevo.padre;
                }else{
                    if(tio.der.color == 'b'){
                        tio.izq.color = 'b';
                        tio.color = 'r';
                        rotacion_derecha(tio);
                        tio = nuevo.padre.der;
                    }
                    tio.color = nuevo.padre.color;
                    nuevo.padre.color = 'b';
                    tio.der.color = 'b';
                    rotacion_izquierda(nuevo.padre);
                    nuevo = raiz;
                }               
            }
            else{
                tio = nuevo.padre.izq;
                if(tio.color == 'r'){
                    tio.color = 'b';
                    nuevo.padre.color = 'r';
                    rotacion_derecha(nuevo.padre);
                    tio = nuevo.padre.izq;
                }
                if(tio.der.color == 'b' && tio.izq.color == 'b'){
                    tio.color = 'r';
                    nuevo = nuevo.padre;
                }else{
                    if(tio.izq.color == 'b'){
                        tio.der.color = 'b';
                        tio.color = 'r';
                        rotacion_izquierda(tio);
                        tio = nuevo.padre.izq;
                    }
                    tio.color = nuevo.padre.color;
                    nuevo.padre.color = 'b';
                    tio.izq.color = 'b';
                    rotacion_derecha(nuevo.padre);
                    nuevo = raiz;
                }
            }
        }
        nuevo.color = 'b';
    }
    
    public void rotacion_izquierda(nodo x){ //método de rotación izquierda
        nodo aux = x.der;
        x.der = aux.izq;
        if (aux.izq != nulo){
            aux.izq.padre = x;
        }
        aux.padre = x.padre;
        if(x.padre == null){
            raiz = aux;
        }else if(x == x.padre.izq){
            x.padre.izq = aux;
        }else{
            x.padre.der = aux;
        }
        aux.izq = x;
        x.padre = aux;
    }

    public void rotacion_derecha(nodo x){ //método de rotación derecha
        nodo aux = x.izq;
        x.izq = aux.der;
        if (aux.der != nulo) {
            aux.der.padre = x;
        }
        aux.padre = x.padre;
        if (x.padre == null) {
            raiz = aux;
        }else if(x == x.padre.der){
            x.padre.der = aux;
        }else{
            x.padre.izq = aux;
        }
        aux.der = x;
        x.padre = aux;
    }
    
    public nodo crear_nodo(int dato){ //se crea el nodo
        nodo nuevo = new nodo();
        nuevo.dato = dato;
        nuevo.izq = nulo;
        nuevo.der = nulo;
        nuevo.padre = null; 
        nuevo.color = 'r';
        
        return nuevo;
    }
           
}
class nodo{ //la clase nodo
    int dato;
    char color;
    nodo padre;
    nodo izq;
    nodo der;
}

