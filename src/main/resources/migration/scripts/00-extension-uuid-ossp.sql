--liquibase formatted sql

-- changeset VasiliySachkov:add-uuid-extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
