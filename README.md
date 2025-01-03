Pasos a seguir:

- Levantar jenkins y sonarqube en docker con docker-compose: docker-compose up -d --build
- Al levantar Jenkins con docker es necesario obtener la contraseña generada automaticamente dentro de la consola de jenkins en docker

  - Configurar Maven dentro de Jenkins: 
  	- Ir a "administrar jenkins/tools"
  	- En "Git installations" activar la opción "Instalar autimaticamente"
  	- En  instalaciones de Maven, añadir una nueva instalación con nombre M3 y seleccionar instalar
	  automáticamente

  - Crear pipeline: video: https://www.youtube.com/watch?v=nYM1gaaG2QY&list=PLjNII-Jkdjfz5EXWlGMBRk63PC8uJsHMo&index=10
  	- Crear una nueva tarea o job dentro de Jenkins
  	- Nombrar la tarea y seleccionar el tipo Pipeline, luego configurar la tarea
  	- seleccionar desechar ejecuciones antiguas y especificar un máximo de 2 builds
  	- seleccionar "GitHub proyect" e ingresar la URL del repositorio de GitHub
  	- seleccionar "GitHub hook trigger for GITScm polling"
  	- seleccionar "Consultar repositorio (SCM)" o "Poll SCM" e ingresar en el cuadro de texto: "* * * * *"
  	- En la sección de "Pipeline" Definition seleccionar "Pipeline script from SCM"
  	- En la sección de "SCM" seleccionar "Git"
  	- Añadir la URL del repositorio en "Repository URL"
  	- En "Branches to build" cambiar "*/master" a "*/main"
  	- guardar configuración


- OWASP dependency check: - videos: https://www.youtube.com/watch?v=V3h_nWPTMu0 https://www.youtube.com/watch?v=bImOWD4b6o8
	- Ir a administrar Jenkins/plugins
	- en "Available Plugins" buscar owasp e instalar OWASP Dependency-check plugin
	- ir a "administrar jenkins/tools" hasta "instalaciones de Dependency-Check "
	- añadir dependency check e ingresar nombre "owasp-dc"
	- seleccionar "instalar automáticamente" con instalacion desde github



- Sonarqube en Jenkins: video: https://www.youtube.com/watch?v=KsTMy0920go


	 - Dentro de Jenkins:
		- Ir a "administrar Jenkins/plugins"
		- en "Available Plugins" buscar sonarqube e instalar sonarqube scanner, luego seleccionar la opcion "reiniciar jenkins"
		- una vez instalado, dentro de "administrar jenkins/system"
		- En "SonarQube servers" seleccionar "Environment variables"
		- Añadir servidor de sonarqube y definir su nombre, en este caso es "sq1"
		- Ingresar la URL de sonarqube, si sonarqube corre localmente esta es "http://localhost:9000". Con docker: "http://sonarqube:9000"

	- Dentro de sonarqube:
		- ingresar con username y password: "admin"
		- cambiar la contraseña, ej: "Password123."
		- ir a "Administration/security/users"
		- en la columna "Tokens" dentro del usuario admin seleccionar los tres puntos
		- en generate tokens ingresar un nombre (ej:jenkins) y click en generar
		- Copiar el token generado (ejemplo de token: squ_47aa057b937f5db336f986b5c11332bca6099019)

	- Dentro de Jenkins:
		- Una vez generado el token de sonarqube y dentro de "SonarQube servers", añadir un "Server authentication token"
		- En "kind" seleccionar "secret text"
		- En "secret" ingresar el token generado
		- En "id" y description ingresar "jenkins-sonar"
		- en "Server authentication token" seleccionar la autenticación creada con nombre "jenkins-sonar"
		- guardar la configuración
		- ir a "administrar Jenkins/tools" 
		- añadir "SonarQube Scanner" con nombre "sq1" y seleccionar "instalar automáticamente"


- Para ejecutar el pipeline seleccionar la tarea creada dentro de Jenkins y seleccionar "construir ahora"
- 

- Sonarqube Quality gate (solo con docker): videos: https://www.youtube.com/watch?v=KsTMy0920go https://www.youtube.com/watch?app=desktop&v=jrksCo-M1Ns&t=59s
	-  Dentro de sonarqube ir a "Administration/configuration/webhooks"
        -  Crear webhook con nombre "Jenkins" y URL: http://jenkins:8080/sonarqube-webhook/
        -  ir a "Quality gates" y crear quality gate con nombre "pep2"
        -  añadir condición:  vulnerability: 0 tanto para "new code" como para "overall code"
        -  definir quality gate como default (set as default)
  
- Creacion imagen docker, despliegue docker y OWASP ZAP (NO SE LOGRO IMPLEMENTAR CORRECTAMENE):
	-  Para que Jenkins sea capaz de correr comandos de docker es necesario instalar docker dentro del contenedor de Jenkins
 	-  Dentro de Docker desktop, acceder al contenedor de Jenkins y abrir terminal
  	-  Dentro de la terminar utilizar los siguientes comandos: "apt-get update" y "apt-get install -y docker.io"
  	-  Notar que la instalacion de docker va a tardar un tiempo
  	-  Crear nuevo job en jenkins con nombre: deploy app
  	-  Establecer como pipeline, copiando y pegando el script del archivo Jenkinsfile_2 en el repositorio  
