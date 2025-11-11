# 🧪 Guía de Verificación y Pruebas - Orchestrator Bounded Context

## 📋 Índice
1. [Configuración de Base de Datos](#configuración-de-base-de-datos)
2. [Verificación de Tablas](#verificación-de-tablas)
3. [Endpoints Disponibles](#endpoints-disponibles)
4. [Pruebas con cURL](#pruebas-con-curl)
5. [Pruebas con Postman/Thunder Client](#pruebas-con-postman)
6. [Verificación con Swagger](#verificación-con-swagger)
7. [Integración con Frontend](#integración-con-frontend)

---

## 🗄️ Configuración de Base de Datos

### Prerequisitos
- PostgreSQL instalado y corriendo en `localhost:5432`
- Base de datos: `postgres`
- Usuario: `postgres`
- Contraseña: `password`

### Verificar conexión PostgreSQL

```powershell
# Verificar que PostgreSQL está corriendo
Get-Service -Name postgresql*

# Conectar a PostgreSQL (opcional)
psql -U postgres -d postgres
```

### Variables de Entorno (opcional)
Puedes sobrescribir la configuración usando variables de entorno:

```powershell
$env:DATABASE_URL="jdbc:postgresql://localhost:5432/postgres"
$env:DATABASE_USER="postgres"
$env:DATABASE_PASSWORD="password"
```

---

## 📊 Verificación de Tablas

Una vez que la aplicación esté corriendo, verifica que se crearon las tablas:

```sql
-- Conectar a PostgreSQL
psql -U postgres -d postgres

-- Listar todas las tablas
\dt

-- Ver estructura de tablas del Orchestrator
\d alert_settings
\d posture_settings

-- Verificar que las tablas existen
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
AND table_name IN ('alert_settings', 'posture_settings');
```

### Estructura Esperada de Tablas

**Tabla: alert_settings**
- id (bigint, PK)
- user_id (bigint, NOT NULL)
- visual_alerts_enabled (boolean, NOT NULL)
- sound_alerts_enabled (boolean, NOT NULL)
- alert_volume (integer, NOT NULL)
- alert_interval (integer, NOT NULL)
- created_at (timestamp, NOT NULL)
- updated_at (timestamp, NOT NULL)

**Tabla: posture_settings**
- id (bigint, PK)
- user_id (bigint, NOT NULL)
- posture_sensitivity (integer, NOT NULL)
- shoulder_angle_threshold (integer, NOT NULL)
- head_angle_threshold (integer, NOT NULL)
- sampling_frequency (integer, NOT NULL)
- show_skeleton (boolean, NOT NULL)
- shoulder_angle (integer) - embedded
- neck_angle (integer) - embedded
- back_angle (integer) - embedded
- head_tilt (integer) - embedded
- created_at (timestamp, NOT NULL)
- updated_at (timestamp, NOT NULL)

---

## 🌐 Endpoints Disponibles

### Alert Settings API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/orchestrator/alerts-settings` | Crear configuración de alertas |
| GET | `/api/v1/orchestrator/alerts-settings/user/{userId}` | Obtener por usuario |
| GET | `/api/v1/orchestrator/alerts-settings/{settingId}` | Obtener por ID |
| PUT | `/api/v1/orchestrator/alerts-settings/{settingId}` | Actualizar configuración |
| DELETE | `/api/v1/orchestrator/alerts-settings/{settingId}` | Eliminar configuración |

### Posture Settings API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/orchestrator/posture-settings` | Crear configuración postural |
| GET | `/api/v1/orchestrator/posture-settings/user/{userId}` | Obtener por usuario |
| GET | `/api/v1/orchestrator/posture-settings/{settingId}` | Obtener por ID |
| PUT | `/api/v1/orchestrator/posture-settings/{settingId}` | Actualizar configuración |
| DELETE | `/api/v1/orchestrator/posture-settings/{settingId}` | Eliminar configuración |

---

## 🔧 Pruebas con cURL

### Alert Settings

#### 1. Crear Alert Settings
```powershell
curl -X POST http://localhost:8080/api/v1/orchestrator/alerts-settings `
  -H "Content-Type: application/json" `
  -d '{
    "userId": 1,
    "visualAlertsEnabled": true,
    "soundAlertsEnabled": true,
    "alertVolume": 75,
    "alertInterval": 30
  }'
```

#### 2. Obtener Alert Settings por Usuario
```powershell
curl http://localhost:8080/api/v1/orchestrator/alerts-settings/user/1
```

#### 3. Obtener Alert Settings por ID
```powershell
curl http://localhost:8080/api/v1/orchestrator/alerts-settings/1
```

#### 4. Actualizar Alert Settings
```powershell
curl -X PUT http://localhost:8080/api/v1/orchestrator/alerts-settings/1 `
  -H "Content-Type: application/json" `
  -d '{
    "visualAlertsEnabled": true,
    "soundAlertsEnabled": false,
    "alertVolume": 50,
    "alertInterval": 60
  }'
```

#### 5. Eliminar Alert Settings
```powershell
curl -X DELETE http://localhost:8080/api/v1/orchestrator/alerts-settings/1
```

### Posture Settings

#### 1. Crear Posture Settings
```powershell
curl -X POST http://localhost:8080/api/v1/orchestrator/posture-settings `
  -H "Content-Type: application/json" `
  -d '{
    "userId": 1,
    "postureSensitivity": 75,
    "shoulderAngleThreshold": 20,
    "headAngleThreshold": 15,
    "samplingFrequency": 2,
    "showSkeleton": true,
    "postureThresholds": {
      "shoulderAngle": 20,
      "neckAngle": 15,
      "backAngle": 25,
      "headTilt": 10
    }
  }'
```

#### 2. Obtener Posture Settings por Usuario
```powershell
curl http://localhost:8080/api/v1/orchestrator/posture-settings/user/1
```

#### 3. Obtener Posture Settings por ID
```powershell
curl http://localhost:8080/api/v1/orchestrator/posture-settings/1
```

#### 4. Actualizar Posture Settings
```powershell
curl -X PUT http://localhost:8080/api/v1/orchestrator/posture-settings/1 `
  -H "Content-Type: application/json" `
  -d '{
    "postureSensitivity": 80,
    "shoulderAngleThreshold": 18,
    "headAngleThreshold": 12,
    "samplingFrequency": 1,
    "showSkeleton": false,
    "postureThresholds": {
      "shoulderAngle": 18,
      "neckAngle": 12,
      "backAngle": 22,
      "headTilt": 8
    }
  }'
```

#### 5. Eliminar Posture Settings
```powershell
curl -X DELETE http://localhost:8080/api/v1/orchestrator/posture-settings/1
```

---

## 📮 Pruebas con Postman/Thunder Client

### Colección JSON para Postman

Importa esta colección en Postman:

```json
{
  "info": {
    "name": "Orchestrator API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Alert Settings",
      "item": [
        {
          "name": "Create Alert Settings",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"userId\": 1,\n  \"visualAlertsEnabled\": true,\n  \"soundAlertsEnabled\": true,\n  \"alertVolume\": 75,\n  \"alertInterval\": 30\n}"
            },
            "url": {"raw": "http://localhost:8080/api/v1/orchestrator/alerts-settings"}
          }
        },
        {
          "name": "Get Alert Settings by User",
          "request": {
            "method": "GET",
            "url": {"raw": "http://localhost:8080/api/v1/orchestrator/alerts-settings/user/1"}
          }
        }
      ]
    },
    {
      "name": "Posture Settings",
      "item": [
        {
          "name": "Create Posture Settings",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"userId\": 1,\n  \"postureSensitivity\": 75,\n  \"shoulderAngleThreshold\": 20,\n  \"headAngleThreshold\": 15,\n  \"samplingFrequency\": 2,\n  \"showSkeleton\": true,\n  \"postureThresholds\": {\n    \"shoulderAngle\": 20,\n    \"neckAngle\": 15,\n    \"backAngle\": 25,\n    \"headTilt\": 10\n  }\n}"
            },
            "url": {"raw": "http://localhost:8080/api/v1/orchestrator/posture-settings"}
          }
        },
        {
          "name": "Get Posture Settings by User",
          "request": {
            "method": "GET",
            "url": {"raw": "http://localhost:8080/api/v1/orchestrator/posture-settings/user/1"}
          }
        }
      ]
    }
  ]
}
```

---

## 📖 Verificación con Swagger

### Acceder a Swagger UI

1. Inicia la aplicación: `./mvnw spring-boot:run`
2. Abre el navegador en: `http://localhost:8080/swagger-ui.html`
3. Busca las secciones:
   - **Alert Settings** - Alert Settings Management Endpoints
   - **Posture Settings** - Posture Settings Management Endpoints

### Probar desde Swagger

1. Expande el endpoint que deseas probar
2. Click en "Try it out"
3. Ingresa los parámetros requeridos
4. Click en "Execute"
5. Verifica la respuesta

---

## 🎨 Integración con Frontend

### Configuración CORS (si es necesario)

Si el frontend está en un dominio diferente, agrega configuración CORS:

```java
// Crear: src/main/java/com/nrgserver/ergovision/shared/infrastructure/web/config/CorsConfig.java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### Ejemplo de Integración con Frontend (React/Angular/Vue)

#### Axios (React/Vue)

```javascript
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1/orchestrator';

// Crear Alert Settings
export const createAlertSettings = async (userId, settings) => {
  const response = await axios.post(`${API_BASE_URL}/alerts-settings`, {
    userId,
    visualAlertsEnabled: settings.visualAlertsEnabled,
    soundAlertsEnabled: settings.soundAlertsEnabled,
    alertVolume: settings.alertVolume,
    alertInterval: settings.alertInterval
  });
  return response.data;
};

// Obtener Alert Settings por usuario
export const getAlertSettingsByUser = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/alerts-settings/user/${userId}`);
  return response.data;
};

// Actualizar Alert Settings
export const updateAlertSettings = async (settingId, settings) => {
  const response = await axios.put(`${API_BASE_URL}/alerts-settings/${settingId}`, settings);
  return response.data;
};

// Crear Posture Settings
export const createPostureSettings = async (userId, settings) => {
  const response = await axios.post(`${API_BASE_URL}/posture-settings`, {
    userId,
    postureSensitivity: settings.postureSensitivity,
    shoulderAngleThreshold: settings.shoulderAngleThreshold,
    headAngleThreshold: settings.headAngleThreshold,
    samplingFrequency: settings.samplingFrequency,
    showSkeleton: settings.showSkeleton,
    postureThresholds: settings.postureThresholds
  });
  return response.data;
};

// Obtener Posture Settings por usuario
export const getPostureSettingsByUser = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/posture-settings/user/${userId}`);
  return response.data;
};
```

#### HttpClient (Angular)

```typescript
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrchestratorService {
  private apiUrl = 'http://localhost:8080/api/v1/orchestrator';

  constructor(private http: HttpClient) {}

  // Alert Settings
  createAlertSettings(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/alerts-settings`, data);
  }

  getAlertSettingsByUser(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/alerts-settings/user/${userId}`);
  }

  updateAlertSettings(settingId: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/alerts-settings/${settingId}`, data);
  }

  // Posture Settings
  createPostureSettings(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/posture-settings`, data);
  }

  getPostureSettingsByUser(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/posture-settings/user/${userId}`);
  }

  updatePostureSettings(settingId: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/posture-settings/${settingId}`, data);
  }
}
```

### Tipos TypeScript (para Frontend)

```typescript
export interface AlertSetting {
  id: number;
  userId: number;
  visualAlertsEnabled: boolean;
  soundAlertsEnabled: boolean;
  alertVolume: number;
  alertInterval: number;
}

export interface PostureThresholds {
  shoulderAngle: number;
  neckAngle: number;
  backAngle: number;
  headTilt: number;
}

export interface PostureSetting {
  id: number;
  userId: number;
  postureSensitivity: number;
  shoulderAngleThreshold: number;
  headAngleThreshold: number;
  samplingFrequency: number;
  showSkeleton: boolean;
  postureThresholds: PostureThresholds;
}
```

---

## ✅ Checklist de Verificación

### Backend
- [ ] PostgreSQL está corriendo
- [ ] Base de datos `postgres` existe
- [ ] Aplicación Spring Boot inicia sin errores
- [ ] Tablas `alert_settings` y `posture_settings` se crearon
- [ ] Swagger UI accesible en `http://localhost:8080/swagger-ui.html`
- [ ] Endpoints responden correctamente

### Pruebas Funcionales
- [ ] Crear Alert Settings funciona
- [ ] Obtener Alert Settings por usuario funciona
- [ ] Actualizar Alert Settings funciona
- [ ] Eliminar Alert Settings funciona
- [ ] Crear Posture Settings funciona
- [ ] Obtener Posture Settings por usuario funciona
- [ ] Actualizar Posture Settings funciona
- [ ] Eliminar Posture Settings funciona

### Validaciones
- [ ] Volumen debe estar entre 0-100
- [ ] Sensibilidad debe estar entre 0-100
- [ ] Ángulos deben estar entre 0-90
- [ ] Intervalo de alerta debe ser mínimo 1 segundo
- [ ] No se pueden crear configuraciones duplicadas para un usuario

### Integración Frontend
- [ ] CORS configurado (si es necesario)
- [ ] API responde con JSON correcto
- [ ] Códigos de estado HTTP correctos
- [ ] Manejo de errores funciona

---

## 🐛 Troubleshooting

### Error: "Unable to connect to database"
```powershell
# Verificar que PostgreSQL está corriendo
Get-Service -Name postgresql*

# Iniciar PostgreSQL si está detenido
Start-Service -Name postgresql-x64-15  # Ajusta el nombre según tu versión
```

### Error: "Table already exists"
Cambia `spring.jpa.hibernate.ddl-auto` a `update` en lugar de `create` en `application.properties`

### Error: "Port 8080 is already in use"
```powershell
# Encontrar proceso usando el puerto 8080
netstat -ano | findstr :8080

# Matar el proceso (reemplaza PID con el número obtenido)
taskkill /PID <PID> /F
```

### Error de validación
Verifica que los datos cumplen con las restricciones:
- `alertVolume`: 0-100
- `postureSensitivity`: 0-100
- `shoulderAngleThreshold`: 0-90
- `headAngleThreshold`: 0-90

---

## 📝 Notas Adicionales

1. **Valores por defecto**: Al crear settings con solo userId, se usan valores por defecto sensatos
2. **Un usuario, una configuración**: No se pueden crear múltiples configuraciones del mismo tipo para un usuario
3. **Auditoría**: `createdAt` y `updatedAt` se manejan automáticamente
4. **Naming Strategy**: Las tablas usan snake_case y están pluralizadas automáticamente

---

## 🚀 Próximos Pasos

1. Implementar autenticación JWT
2. Agregar validaciones de permisos
3. Implementar caché con Redis
4. Agregar métricas y monitoreo
5. Crear tests unitarios e integración
