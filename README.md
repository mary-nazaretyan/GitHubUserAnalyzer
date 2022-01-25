# GitHub Users Analyzer

### Batch job

Grabs GitHub users information with provided token (up to 10.000 users).<br/> Please provide your token, this one is revoked.
```
disqo.mary.git.analyzer.restriction=10000
disqo.mary.git.analyzer.token=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
```
Filters users based on configuration:
```
disqo.mary.git.analyzer.repos=10
disqo.mary.git.analyzer.followers=5
disqo.mary.git.analyzer.created-after=2008-01-01
```
Stores filtered users in Postgres


## REST API
Endpoints' information can be found in "docs/openapi/api.yaml"


**Database**

The application is using postgres database.<br/>
Unit tests are using embedded h2 database.