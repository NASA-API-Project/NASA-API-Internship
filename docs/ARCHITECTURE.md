graph TB
    %% External Systems
    NASA[NASA Public APIs] 
    DB[(MySQL Database)]
    USER[User Browser]
    
    %% Application Layers
    subgraph Backend["Backend Application"]
        %% Core Components
        MAIN[NasaApiApplication]
        
        %% Controller Layer
        subgraph Controllers["Controller Layer"]
            REST[NasaApiController]
            MVC[NasaMvcController]
            LOGIN[LoginController]
            JWT[JwtAuthenticationResource]
        end
        
        %% Service Layer
        subgraph Services["Service Layer"]
            SVC_INTERFACE[NasaApiService]
            SVC_IMPL[NasaApiServiceImpl]
            REST_TEMPLATE[RestTemplate]
        end
        
        %% Repository Layer
        subgraph Repositories["Repository Layer"]
            REPO[NasaRepository]
        end
        
        %% Model Layer
        subgraph Models["Model Layer"]
            APOD[NasaApod]
            MARS_ROVER[MarsRover]
            MARS_CAMERA[MarsRoverCamera]
            MARS_PHOTO[MarsRoverPhoto]
            ROVER_REQ[RoverPhotoRequests]
        end
        
        %% Response Layer
        subgraph Responses["Response Layer"]
            ROVER_RESP[MarsRoverPhotosResponse]
            ERROR_RESP[NasaErrorResponse]
        end
        
        %% Exception Layer
        subgraph Exceptions["Exception Handling"]
            GLOBAL_EXC[NasaGlobalExceptionHandler]
            NOT_FOUND[NasaNotFoundException]
        end
        
        %% Security Layer
        subgraph Security["Security Layer"]
            SEC_CONFIG[SecurityConfiguration]
            OPEN_API[OpenApiConfig]
        end
    end
    
    %% Frontend Layer
    subgraph Frontend["Frontend (Thymeleaf Templates)"]
        %% Auth Templates
        subgraph Auth["Authentication Templates"]
            LOGIN_PAGE[custom-login-2.html]
            ACCESS_DENIED[access-denied.html]
        end
        
        %% Content Templates
        subgraph Content["Content Templates"]
            HOME[home-page.html]
            APOD_PAGE[apod.html]
            ROVER_FORM[index.html]
            ROVER_RESULT[result.html]
        end
        
        %% Admin Templates
        subgraph Admin["Admin Templates"]
            APOD_LIST[list-apods.html]
            APOD_UPDATE[update-apod-form.html]
        end
    end
    
    %% Connections
    NASA --> REST_TEMPLATE
    REST_TEMPLATE --> SVC_IMPL
    SVC_IMPL --> SVC_INTERFACE
    SVC_IMPL --> REPO
    REPO --> DB
    
    SVC_INTERFACE --> REST
    SVC_INTERFACE --> MVC
    
    REST --> Models
    REST --> Responses
    REST --> Exceptions
    
    MVC --> Frontend
    LOGIN --> Auth
    
    JWT --> SEC_CONFIG
    REST --> SEC_CONFIG
    
    USER --> Frontend
    USER --> REST
    
    %% Style
    classDef primary fill:#f9f,stroke:#333,stroke-width:1px
    classDef security fill:#bbf,stroke:#33f,stroke-width:1px
    classDef database fill:#bfb,stroke:#3f3,stroke-width:1px
    classDef frontend fill:#ffb,stroke:#bb3,stroke-width:1px
    classDef external fill:#ddd,stroke:#333,stroke-width:1px
    
    class MAIN,REST,MVC,SVC_INTERFACE,SVC_IMPL primary
    class SEC_CONFIG,JWT,OPEN_API security
    class REPO,DB database
    class Frontend,Auth,Content,Admin frontend
    class NASA,USER external