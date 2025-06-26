# Proyecto Kube-Cloud: Despliegue de Microservicios en Google Kubernetes Engine (GKE)

Este README detalla los pasos necesarios para compilar, empaquetar, subir y desplegar los microservicios `ms-citas` y `ms-medicos` en Google Kubernetes Engine (GKE) utilizando Google Cloud Platform (GCP).

## Prerrequisitos

Antes de comenzar, asegúrate de tener instaladas las siguientes herramientas en tu entorno local:

* **Java Development Kit (JDK) 17 o superior**
* **Apache Maven 3.6.3 o superior**
* **Docker Desktop (o Docker Engine)**
* **Google Cloud SDK (`gcloud` CLI)**: Configurado y autenticado con tu cuenta de GCP.
  ```bash
  gcloud auth login
  gcloud config set project <TU_ID_DE_PROYECTO_GCP>
  ```
* **`kubectl`**: Herramienta para interactuar con clústeres de Kubernetes. (Se instala con `gcloud components install kubectl`).
* **Postman (o similar)**: Para realizar pruebas de API.

## Pasos para el Despliegue

### 1. Compilar los Microservicios con Maven

```bash
# Navega al directorio de ms-citas
cd ms-citas
mvn clean package
cd ..

# Navega al directorio de ms-medicos
cd ms-medicos
mvn clean package
cd ..
```

### 2. Generar Imágenes Docker

```bash
# Construir imagen para ms-citas
cd ms-citas
docker build -t ms-citas:1.0 .
cd ..

# Construir imagen para ms-medicos
cd ms-medicos
docker build -t ms-medicos:1.0 .
cd ..
```

### 3. Tagear Imágenes para Artifact Registry

Asegúrate de haber creado el repositorio Docker:

```bash
gcloud artifacts repositories create <TU_NOMBRE_DE_REPOSITORIO_AR> \
  --repository-format=docker \
  --location=<TU_REGION_AR> \
  --description="Repositorio Docker para Kube-Cloud"
```

Autentica Docker y tagea las imágenes:

```bash
gcloud auth configure-docker <TU_REGION_AR>-docker.pkg.dev

# Tagear imagen de ms-citas
docker tag ms-citas:1.0 <TU_REGION_AR>-docker.pkg.dev/<TU_ID_DE_PROYECTO_GCP>/<TU_NOMBRE_DE_REPOSITORIO_AR>/ms-citas:1.0

# Tagear imagen de ms-medicos
docker tag ms-medicos:1.0 <TU_REGION_AR>-docker.pkg.dev/<TU_ID_DE_PROYECTO_GCP>/<TU_NOMBRE_DE_REPOSITORIO_AR>/ms-medicos:1.0
```

### 4. Pushear Imágenes a Artifact Registry

```bash
docker push <TU_REGION_AR>-docker.pkg.dev/<TU_ID_DE_PROYECTO_GCP>/<TU_NOMBRE_DE_REPOSITORIO_AR>/ms-citas:1.0
docker push <TU_REGION_AR>-docker.pkg.dev/<TU_ID_DE_PROYECTO_GCP>/<TU_NOMBRE_DE_REPOSITORIO_AR>/ms-medicos:1.0
```

### 5. Habilitar Google Kubernetes Engine (GKE) API

```bash
gcloud services enable container.googleapis.com
```

### 6. Crear un Clúster Autopilot en GKE

```bash
gcloud container clusters create-auto autopilot-cluster-1 --region <TU_REGION_GCP>
gcloud container clusters get-credentials autopilot-cluster-1 --region <TU_REGION_GCP>
```

### 7. Realizar Deploy del Microservicio `ms-citas`

Guarda el siguiente contenido como `ms-citas-deployment.yaml`:

```yaml
# ms-citas-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-citas-deployment
  labels:
    app: ms-citas
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ms-citas
  template:
    metadata:
      labels:
        app: ms-citas
    spec:
      containers:
      - name: ms-citas
        image: <TU_REGION_AR>-docker.pkg.dev/<TU_ID_DE_PROYECTO_GCP>/<TU_NOMBRE_DE_REPOSITORIO_AR>/ms-citas:1.0
        ports:
        - containerPort: 9091
---
apiVersion: v1
kind: Service
metadata:
  name: ms-citas-service
spec:
  selector:
    app: ms-citas
  ports:
    - protocol: TCP
      port: 9091
      targetPort: 9091
  type: LoadBalancer
```

Aplica el despliegue:

```bash
kubectl apply -f ms-citas-deployment.yaml
kubectl get service ms-citas-service
```

### 8. Realizar Deploy del Microservicio `ms-medicos`

Guarda el siguiente contenido como `ms-medicos-deployment.yaml`:

```yaml
# ms-medicos-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-medicos-deployment
  labels:
    app: ms-medicos
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ms-medicos
  template:
    metadata:
      labels:
        app: ms-medicos
    spec:
      containers:
      - name: ms-medicos
        image: <TU_REGION_AR>-docker.pkg.dev/<TU_ID_DE_PROYECTO_GCP>/<TU_NOMBRE_DE_REPOSITORIO_AR>/ms-medicos:1.0
        ports:
        - containerPort: 9092
---
apiVersion: v1
kind: Service
metadata:
  name: ms-medicos-service
spec:
  selector:
    app: ms-medicos
  ports:
    - protocol: TCP
      port: 9092
      targetPort: 9092
  type: LoadBalancer
```

Aplica el despliegue:

```bash
kubectl apply -f ms-medicos-deployment.yaml
kubectl get service ms-medicos-service
```

```bash
kubectl get all
```
![9 getall-gcp](https://github.com/user-attachments/assets/b7b2ecf3-086c-4031-9709-106561ff1b1b)

### 9. Pruebas Mediante Postman

1. Espera a que ambos servicios tengan una `EXTERNAL-IP`.
2. Abre Postman.
3. Realiza pruebas sobre:

```
http://<EXTERNAL_IP_MS_CITAS>/usuario
http://<EXTERNAL_IP_MS_CITAS>/citas
http://<EXTERNAL_IP_MS_MEDICOS>/medico
```

1. Crear usuario

![1 registra-usuario](https://github.com/user-attachments/assets/3db0a71c-84cb-40c2-aa4e-2e04b0bbf202)
![2 resultado-usuario](https://github.com/user-attachments/assets/5e899964-48b7-4ed4-a834-ab3ca801a5a0)

2. Crear medico
   
![3 registra-medico](https://github.com/user-attachments/assets/bc5557c5-9491-442c-924e-a19fd71a7f89)
![4 resultado-medico](https://github.com/user-attachments/assets/56aab328-60ee-4487-a0f6-0d73fb78ccdd)

3. Crear cita
   
![5 registro-cita](https://github.com/user-attachments/assets/bf240479-f5ca-454d-aa99-ffe8cac80596)
![6 resultado-cita](https://github.com/user-attachments/assets/2dc4ba13-1236-4dd3-a3a8-93cfdb50f159)

4. Ver Cita
   
![7 obtener-cita](https://github.com/user-attachments/assets/459258e9-f2d3-4fab-99da-c4b88658e93d)
![8 resultado-cita](https://github.com/user-attachments/assets/37ea81fb-546e-49ca-a412-35a21e56f1f4)











