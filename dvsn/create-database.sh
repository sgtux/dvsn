export PGPASSWORD=Postgres123
psql -h 172.45.45.10 -U postgres -d postgres -a -f ./api/db-script/script.sql
psql -h 172.45.45.10 -U postgres -d postgres -a -f ./api/db-script/script-rainbow-table.sql
