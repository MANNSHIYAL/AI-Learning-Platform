daelp/
├── api-gateway/              # Entry point, routing, and load balancing [Added]
├── auth-service/             # Identity management & JWT authentication [Added]
├── task-service/             # Core business logic for task orchestration [Added]
├── ai-service/               # LLM integration and AI logic [Added]
├── configuration-service/    # Centralized external configuration (Config Server) [Added]
├── discovery-service/        # Service registration and discovery (Eureka/Consul) [Added]
├── contract/                 # API definitions and shared schemas [Added]
├── embedding-service/        # Vectorization and text embeddings [Added]
└── frontend-angular/         # UI Layer (Managed as a separate project) [Added]
├── notification-service/     # Real-time alerts, email, and push notifications
├── monitoring-service/       # Health checks, metrics, and dashboarding (Prometheus/Grafana)
├── coding-service/           # Code generation and analysis logic
├── log-service/              # Centralized logging aggregator (ELK/EFK stack)
├── document-service/         # File management and document processing
├── evaluation-service/       # AI model performance and output scoring
├── common-lib/               # Shared utilities, exceptions, and DTOs
