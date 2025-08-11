export VAULT_TOKEN='vaulttoken'
export VAULT_ADDR="http://host.docker.internal:8200"
vault kv put secret/user-service db.username=root db.password=22572