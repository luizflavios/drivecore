# Contexto de Implementação - Sistema de Geração de Relatórios

## Data de Criação

19 de Novembro de 2025

## Objetivo

Implementar um sistema robusto e genérico de geração de relatórios para o
DriveCore, com as seguintes características:

- Relatórios dinâmicos baseados em tipos (enum)
- Suporte a múltiplas estratégias de filtros
- Armazenamento em S3 com URLs assinadas
- Provider genérico de anexos para facilitar extensões futuras

---

## Requisitos Funcionais

### 1. Tipos de Relatórios

- Inicial: `couplingHistory` (histórico de acoplamento de caminhão/reboque com
  motorista)
- Extensível para futuros tipos

### 2. Filtros Dinâmicos

- Cada relatório pode ter filtros específicos
- Exemplo `couplingHistory`: `truckId`, `trailerId`, `employerId` (opcionais)
- Usar `FilterCriteriaSpecification` existente para queries dinâmicas no banco

### 3. Geração de Arquivo

- Gerar relatório em Excel usando Apache POI
- Estrutura de dados conforme tipo de relatório

### 4. Armazenamento em S3

- Pasta estruturada: `reports/{reportName}/{timestamp}.xlsx`
- Exemplo:
  `reports/couplingHistory/couplingHistory_2025-11-19T10:30:45.123Z.xlsx`
- Retornar URL assinada (presigned URL) na resposta

### 5. Genericidade

- Implementar `AttachmentProvider<T>` como interface
- Implementação: `S3AttachmentProvider`
- Factory para injeção de dependência

---

## Requisitos Técnicos

### Stack Existente a Aproveitar

- ✅ `FilterCriteriaSpecification` - Filtros dinâmicos JPA
- ✅ `S3ClientFactory` - Factory do S3 (S3Client + S3Presigner)
- ✅ Apache POI - Geração de Excel
- ✅ Spring Data JPA - Persistência
- ✅ Lombok - Redução de boilerplate
- ✅ MapStruct - Mapeamento DTO

### Arquitetura Clean Architecture

Camadas a serem implementadas:

1. **Controller**: `ReportController` (já existe básico)
2. **Application**: `ReportApplicationService` (orquestração)
3. **Domain**:
    - `ReportGenerationService` (orquestra geração)
    - `ReportStrategy` interface (padrão Strategy)
    - Implementações: `CouplingHistoryReportStrategy`
4. **Infrastructure**:
    - `AttachmentProvider<T>` (interface)
    - `S3AttachmentProvider` (implementação)
    - `AttachmentProviderFactory` (factory)

---

## Plano de Implementação em Fases

### FASE 1: Estrutura Base e Enums ✅ (A FAZER)

**✅ CONCLUÍDO EM 19-11-2025**

#### Arquivos Criados:

1. **ReportNameEnum.java** ✅
    - Enum extensível com tipos de relatórios
    - Método `fromCode(String)` para conversão dinâmica
    - Atributos: code (ex: "couplingHistory"), description

2. **InvalidReportTypeException.java** ✅
    - RuntimeException customizada para tipos inválidos
    - Construtores com message e cause

3. **ReportGenerationException.java** ✅
    - RuntimeException para erros na geração
    - Usada para query, IO, S3 errors

4. **ReportGenerationResult.java** ✅
    - Modelo de retorno com presigned URL
    - Contém: reportName, fileName, presignedUrl, timestamp, fileSize, bucket,
      s3Key

5. **ExcelColumnConfig.java** ✅
    - Configuração de coluna para Excel
    - Atributos: headerName, attributeName, width, dataType, format, wrapText,
      position
    - Suporta múltiplos tipos de dados

6. **ReportExcelGenerator.java** ✅
    - Interface para geração de Excel com Apache POI
    - Métodos: generate(), generateMultiSheet()
    - Record interno SheetData para dados multi-aba

7. **PoiReportExcelGenerator.java** ✅
    - Implementação com Apache POI 5.2.5
    - Recursos:
        * Cabeçalho com formatação (negrito, fundo cinza, bordas)
        * Suporte a tipos: DATE, DATETIME, NUMBER, CURRENCY, STRING
        * Formatação customizada de datas e moedas
        * Wrap text configurável
        * Congelar primeira linha (freeze pane)
        * Auto-ajuste de colunas
        * Múltiplas abas (sheets)
    - Tratamento de erros com logs detalhados

**Status**: 🟢 FASE 1 COMPLETA
**Arquivos**: 7 criados
**Linhas de Código**: ~400

---

### FASE 2: Provider Genérico de Anexos ✅ (A FAZER)

1. Criar interface `AttachmentProvider<T>` em
   `infrastructure/attachment/provider/`
    - Método:
      `uploadReport(byte[] content, String bucket, String key): String` (retorna
      presigned URL)

2. Criar `S3AttachmentProvider` em `infrastructure/attachment/provider/impl/`
    - Implementação usando S3Client e S3Presigner do factory existente
    - Gerar key estruturado: `reports/{reportName}/{timestamp}.xlsx`
    - Retornar presigned URL com expiração

3. Criar `AttachmentProviderFactory` em
   `infrastructure/attachment/provider/factory/`
    - Bean configuration para injetar S3AttachmentProvider

### FASE 2: Provider Genérico de Anexos ✅ (CONCLUÍDO EM 19-11-2025)

**✅ CONCLUÍDO EM 19-11-2025**

#### Arquivos Criados:

1. **AttachmentProvider.java** ✅
    - Interface genérica com tipo parametrizado `<T>`
    - Métodos: `upload()`, `uploadWithMetadata()`, `getProviderType()`,
      `validateConfiguration()`
    - Extensível para S3, GCS, Azure Blob, etc
    - Retorna presigned URL como String

2. **S3AttachmentProvider.java** ✅
    - Implementação para AWS S3
    - Usa S3Client (upload) + S3Presigner (URLs assinadas)
    - Presigned URL com expiração de 1 hora (configurável)
    - Suporte a metadados customizados
    - Logs detalhados de debug/info/error
    - Content-Type:
      `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`

3. **AttachmentProviderFactory.java** ✅
    - Classe `@Configuration` para criar beans
    - Bean: `attachmentProvider()` retorna S3AttachmentProvider
    - Validação de configuração ao inicializar
    - Injeta S3Client e S3Presigner do factory existente

**Status**: 🟢 FASE 2 COMPLETA
**Arquivos**: 3 criados
**Linhas de Código**: ~250
**Padrão**: GenericProvider Pattern + Factory Pattern

---

### FASE 3: Estratégia de Relatórios (Strategy Pattern) ✅ (A FAZER)

1. Criar interface `ReportStrategy` em `domain/report/strategy/`
    - Método: `generateReport(Map<String, Object> filters): byte[]`
    - Método: `getReportName(): ReportNameEnum`

2. Criar `CouplingHistoryReportStrategy` em `domain/report/strategy/impl/`
    - Query na entidade Coupling/Acoplamento usando Specifications
    - Filtros dinâmicos: truckId, trailerId, employerId
    - Gerar Excel com POI
    - Retornar byte array do arquivo

3. Criar `ReportStrategyFactory` em `domain/report/strategy/factory/`
    - Bean configuration
    - Método: `getStrategy(ReportNameEnum): ReportStrategy`

### FASE 3: Estratégia de Relatórios (Strategy Pattern) ✅ (CONCLUÍDO EM 19-11-2025)

**✅ CONCLUÍDO EM 19-11-2025**

#### Arquivos Criados:

1. **ReportStrategy.java** ✅
    - Interface contrato para estratégias
    - Métodos: `generateReport()`, `getReportName()`, `getReportFileName()`
    - Extensível para novos tipos de relatórios

2. **CouplingHistoryReportStrategy.java** ✅
    - Implementação para Histórico de Acoplamento
    - Query dinâmica via FilterCriteriaSpecification
    - Filtros suportados: truckId, trailerId, employerId
    - Colunas do Excel:
        * Placa Caminhão, Placa Reboque, Motorista
        * KM Inicial, KM Final, Distância
        * Data Finalização, Data Criação
    - Mapeamento automático de dados
    - Logs detalhados (debug/info/error)

3. **ReportStrategyFactory.java** ✅
    - Factory com padrão switch/case
    - Método: `getStrategy(ReportNameEnum): ReportStrategy`
    - Método: `isSupported(ReportNameEnum): boolean`
    - Fácil extensão para novos tipos
    - Validação de tipos suportados

**Status**: 🟢 FASE 3 COMPLETA
**Arquivos**: 3 criados
**Linhas de Código**: ~350
**Padrão**: Strategy Pattern + Factory Pattern
**Entidade Base**: TruckTrailerCombinationEntity

---

### FASE 4: Serviço de Domínio ✅ (A FAZER)

1. Criar `ReportGenerationService` em `domain/report/`
    - Injetar `ReportStrategyFactory` e `AttachmentProvider`
    - Método:
      `generate(ReportNameEnum, Map<String, Object>): ReportGenerationResult`
    - Lógica:
        - Selecionar estratégia via factory
        - Gerar relatório (byte array)
        - Anexar no S3 via provider
        - Retornar URL assinada + metadata

### FASE 4: Serviço de Domínio ✅ (CONCLUÍDO EM 19-11-2025)

**✅ CONCLUÍDO EM 19-11-2025**

#### Arquivos Criados:

1. **ReportGenerationService.java** ✅
    - Orquestra geração e upload de relatórios
    - Métodos: `generate()`, `isReportSupported()`
    - Lógica completa:
        * Obtém timestamp ISO 8601
        * Seleciona estratégia via factory
        * Gera Excel com byte array
        * Constrói S3 key: `reports/{reportName}/{timestamp}.xlsx`
        * Upload com metadados customizados
        * Retorna URL assinada + metadata
    - Injeção via @Value: `app.s3.bucket`
    - Logs detalhados (info/debug/error)

**Status**: 🟢 FASE 4 COMPLETA
**Arquivos**: 1 criado
**Linhas de Código**: ~180

---

### FASE 5: Atualizar Camadas Superiores ✅ (A FAZER)

**✅ CONCLUÍDO EM 19-11-2025**

#### Arquivos Atualizados:

1. **GenerateReportResponseDTO.java** ✅ (ATUALIZADO)
    - Novos campos: presignedUrl, fileName, timestamp, fileSize, bucket, s3Key
    - Campo legado: link (para compatibilidade backward)
    - Anotações: @Builder para construção fluente
    - Documentação completa de cada campo

2. **GenerateReportRequestDTO.java** ✅ (ATUALIZADO)
    - Campo novo: `filters: Map<String, Object>`
    - Suporte a filtros dinâmicos via `@JsonAnySetter`
    - Método: `getConsolidatedFilters()` para consolidar filtros legados + novos
    - Campos legados mantidos: truckId, trailerId, employerId, startDate,
      endDate, contractStatus
    - Documentação com exemplos de uso

3. **ReportApplicationService.java** ✅ (ATUALIZADO)
    - Implementação completa do fluxo
    - Orquestração: conversão de enum → validação → consolidação de filtros →
      geração → mapeamento de response
    - Injeção: ReportGenerationService
    - Tratamento de erros específico
    - Logs em todas as etapas

**Status**: 🟢 FASE 5 COMPLETA
**Arquivos**: 3 atualizados
**Linhas de Código**: ~150

---

## 📊 RESUMO FINAL DE IMPLEMENTAÇÃO

### Total de Arquivos Criados/Atualizados: **16**

#### FASE 1 - Estrutura Base (7 arquivos):

- ✅ ReportNameEnum.java
- ✅ InvalidReportTypeException.java
- ✅ ReportGenerationException.java
- ✅ ReportGenerationResult.java
- ✅ ExcelColumnConfig.java
- ✅ ReportExcelGenerator.java (interface)
- ✅ PoiReportExcelGenerator.java (implementação POI)

#### FASE 2 - Provider Genérico (3 arquivos):

- ✅ AttachmentProvider.java (interface genérica)
- ✅ S3AttachmentProvider.java (implementação S3)
- ✅ AttachmentProviderFactory.java (factory)

#### FASE 3 - Strategy Pattern (3 arquivos):

- ✅ ReportStrategy.java (interface)
- ✅ CouplingHistoryReportStrategy.java (implementação)
- ✅ ReportStrategyFactory.java (factory)

#### FASE 4 - Serviço de Domínio (1 arquivo):

- ✅ ReportGenerationService.java

#### FASE 5 - Camadas Superiores (3 arquivos):

- ✅ GenerateReportResponseDTO.java (ATUALIZADO)
- ✅ GenerateReportRequestDTO.java (ATUALIZADO)
- ✅ ReportApplicationService.java (ATUALIZADO)

---

## 🔄 Fluxo Completo Implementado

```
POST /reports
    ↓
{
  "reportName": "couplingHistory",
  "truckId": "123e4567-e89b-12d3-a456-426614174000",
  "trailerId": "223e4567-e89b-12d3-a456-426614174000"
}
    ↓
ReportController.generateReport()
    ↓
ReportApplicationService.generateReport()
    ├→ Converte "couplingHistory" → ReportNameEnum.COUPLING_HISTORY
    ├→ Valida tipo suportado
    ├→ Consolida filtros (legados + novos)
    ↓
ReportGenerationService.generate()
    ├→ Timestamp: 2025-11-19T10:30:45.123Z
    ├→ ReportStrategyFactory.getStrategy(COUPLING_HISTORY)
    │   └→ CouplingHistoryReportStrategy
    ├→ strategy.generateReport(filters)
    │   ├→ Query BD: TruckTrailerCombination com filtros dinâmicos
    │   ├→ Mapeia dados para Map<String, Object>
    │   ├→ Define colunas Excel (placa, motorista, KM, distância, datas)
    │   └→ PoiReportExcelGenerator.generate() → byte[]
    ├→ S3AttachmentProvider.uploadWithMetadata()
    │   ├→ Upload para S3: reports/couplingHistory/couplingHistory_2025-11-19T10:30:45.123Z.xlsx
    │   ├→ Metadados: report-name, generated-at, filter-truckId, etc
    │   └→ S3Presigner.presignGetObject() → URL assinada (1 hora)
    └→ Retorna ReportGenerationResult
    ↓
Response 200 OK:
{
  "presignedUrl": "https://s3.amazonaws.com/bucket/reports/couplingHistory/...",
  "fileName": "couplingHistory_2025-11-19T10:30:45.123Z.xlsx",
  "timestamp": "2025-11-19T10:30:45.123Z",
  "fileSize": 45678,
  "bucket": "driveco-reports",
  "s3Key": "reports/couplingHistory/couplingHistory_2025-11-19T10:30:45.123Z.xlsx",
  "link": "https://s3.amazonaws.com/bucket/reports/couplingHistory/..." // legado
}
```

---

## 🏗️ Arquitetura Implementada

```
┌─────────────────────────────────────────────────────────────┐
│                     CAMADA CONTROLLER                        │
│  POST /reports → ReportController.generateReport()          │
└────────────────────────┬────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│                 CAMADA APPLICATION                           │
│  ReportApplicationService                                    │
│  - Orquestra DTOs                                            │
│  - Consolida filtros                                         │
│  - Mapeia responses                                          │
└────────────────────────┬────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│                   CAMADA DOMÍNIO                             │
│  ReportGenerationService                                     │
│  ├─ ReportStrategyFactory ──┐                               │
│  │                          ↓                                │
│  │                  ReportStrategy                           │
│  │                  ├─ CouplingHistoryReportStrategy         │
│  │                  └─ (Futuros tipos)                       │
│  └─ AttachmentProvider ──┐                                  │
│                          ↓                                   │
│                   GenericProvider<T>                         │
│                   └─ S3AttachmentProvider                    │
└────────────────────────┬────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│               CAMADA INFRASTRUCTURE                          │
│  ├─ ReportExcelGenerator ──┐                                │
│  │                         ↓                                 │
│  │               PoiReportExcelGenerator                     │
│  │               (Apache POI 5.2.5)                          │
│  │                                                           │
│  ├─ AttachmentProviderFactory                               │
│  │  └─ S3Client + S3Presigner (AWS SDK 2.32.29)             │
│  │                                                           │
│  └─ TruckTrailerCombinationRepository                        │
│     └─ FilterCriteriaSpecification (queries dinâmicas)      │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 Recursos Principais Implementados

### 1. **Geração de Excel com Apache POI**

- ✅ Cabeçalho formatado (negrito, fundo cinza, bordas)
- ✅ Suporte a tipos: DATE, DATETIME, NUMBER, CURRENCY, STRING
- ✅ Formatação customizada (datas, moedas)
- ✅ Congelar primeira linha (freeze pane)
- ✅ Auto-ajuste de colunas
- ✅ Múltiplas abas (sheets)

### 2. **Provider Genérico de Anexos**

- ✅ Interface `AttachmentProvider<T>` extensível
- ✅ Implementação S3 com presigned URLs (1 hora)
- ✅ Suporte a metadados customizados
- ✅ Factory para injeção dinâmica
- ✅ Fácil trocar S3 por GCS, Azure Blob, etc

### 3. **Strategy Pattern para Relatórios**

- ✅ Interface `ReportStrategy` para novos tipos
- ✅ `CouplingHistoryReportStrategy` com:
    * Filtros dinâmicos (truckId, trailerId, employerId)
    * FilterCriteriaSpecification para queries
    * 8 colunas estruturadas
    * Cálculo de distância percorrida
- ✅ Factory para seleção automática

### 4. **Filtros Dinâmicos**

- ✅ `@JsonAnySetter` no DTO para aceitar qualquer filtro
- ✅ `getConsolidatedFilters()` para consolidar legados + novos
- ✅ Backward compatibility com campos antigos

### 5. **S3 Estruturado**

- ✅ Path: `reports/{reportName}/{timestamp}.xlsx`
- ✅ Metadados customizados (report-name, generated-at, filter-*)
- ✅ URLs assinadas com expiração

### 6. **Logs e Tratamento de Erros**

- ✅ Logs debug/info/error em todas as camadas
- ✅ Exceções customizadas (InvalidReportType, ReportGeneration)
- ✅ Stack traces completos

---

## 📋 Próximos Passos Opcionais (Futuro)

1. **Adicionar novos tipos de relatórios**:
    - Criar `TireHistoryReportStrategy`
    - Criar `MaintenanceReportStrategy`
    - Adicionar switch case em `ReportStrategyFactory`

2. **Implementar outros provedores de anexos**:
    - `GcsAttachmentProvider` para Google Cloud Storage
    - `AzureAttachmentProvider` para Azure Blob
    - `LocalFileAttachmentProvider` para desenvolvimento local

3. **Adicionar cache**:
    - Cache de relatórios já gerados
    - Invalidação de cache por filtros

4. **Testes automatizados**:
    - Testes unitários para estratégias
    - Testes de integração com LocalStack
    - Testes de geração de Excel

5. **Permitir agendamento**:
    - Gerar relatórios em background
    - Notificar via email quando pronto

---

## 🚀 Como Usar

### Exemplo de Request:

```json
POST /reports

{
  "reportName": "couplingHistory",
  "truckId": "123e4567-e89b-12d3-a456-426614174000",
  "trailerId": "223e4567-e89b-12d3-a456-426614174000"
}
```

### Exemplo de Response:

```json
{
  "presignedUrl": "https://localhost:4566/driveco-reports/reports/couplingHistory/couplingHistory_2025-11-19T10%3A30%3A45.123Z.xlsx?X-Amz-Algorithm=...",
  "fileName": "couplingHistory_2025-11-19T10:30:45.123Z.xlsx",
  "timestamp": "2025-11-19T10:30:45.123Z",
  "fileSize": 45678,
  "bucket": "driveco-reports",
  "s3Key": "reports/couplingHistory/couplingHistory_2025-11-19T10:30:45.123Z.xlsx",
  "link": "https://localhost:4566/driveco-reports/reports/couplingHistory/couplingHistory_2025-11-19T10%3A30%3A45.123Z.xlsx?X-Amz-Algorithm=..."
}
```

---

**Status Final**: 🟢 IMPLEMENTAÇÃO COMPLETA
**Total de Linhas de Código**: ~1200 linhas
**Padrões Utilizados**: Strategy Pattern, Factory Pattern, Provider Pattern,
Clean Architecture
**Todas as FASES Concluídas**: FASE 1 ✅ | FASE 2 ✅ | FASE 3 ✅ | FASE 4 ✅ | FASE
5 ✅
**Data Conclusão**: 19-11-2025
**Responsável**: GitHub Copilot
