
INSERT INTO `rf_document` (`id`, `document_source_id`, `name`, `deskripsi`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('0ffd02f2-b5c8-11f0-93cd-00090ffe0001', '996b8a71-b5c7-11f0-93cd-00090ffe0001', 'Deviasi', NULL, 1, '2025-10-30 19:38:51', '2025-10-30 19:38:51', 0),
	('107a25f4-b5c8-11f0-93cd-00090ffe0001', '996b8a71-b5c7-11f0-93cd-00090ffe0001', 'SPK', NULL, 1, '2025-10-30 19:38:52', '2025-10-30 19:38:52', 0),
	('10e98aa7-b5c8-11f0-93cd-00090ffe0001', '996b8a71-b5c7-11f0-93cd-00090ffe0001', 'Draft Client', NULL, 1, '2025-10-30 19:38:53', '2025-10-30 19:38:53', 0),
	('1faf317f-b5c8-11f0-93cd-00090ffe0001', '99e9ab54-b5c7-11f0-93cd-00090ffe0001', 'Deviasi', NULL, 1, '2025-10-30 19:39:17', '2025-10-30 19:39:17', 0),
	('203bed8c-b5c8-11f0-93cd-00090ffe0001', '99e9ab54-b5c7-11f0-93cd-00090ffe0001', 'SPK', NULL, 1, '2025-10-30 19:39:18', '2025-10-30 19:39:18', 0);

-- Dumping data for table ldr.rf_document_source: ~2 rows (approximately)
INSERT INTO `rf_document_source` (`id`, `name`, `description`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('996b8a71-b5c7-11f0-93cd-00090ffe0001', 'DRAFT KLIEN', NULL, 1, '2025-10-30 19:35:32', '2025-10-30 19:35:32', 0),
	('99e9ab54-b5c7-11f0-93cd-00090ffe0001', 'DRAFT PT DIKA', NULL, 1, '2025-10-30 19:35:33', '2025-10-30 19:35:33', 0);

-- Dumping data for table ldr.rf_document_type: ~0 rows (approximately)

-- Dumping data for table ldr.rf_employment_status: ~4 rows (approximately)
INSERT INTO `rf_employment_status` (`id`, `name`, `description`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('68868116-b5c7-11f0-93cd-00090ffe0001', 'PKWT', NULL, 1, '2025-10-30 19:34:10', '2025-10-30 19:34:10', 0),
	('691be668-b5c7-11f0-93cd-00090ffe0001', 'MITRA', NULL, 1, '2025-10-30 19:34:11', '2025-10-30 19:34:11', 0),
	('69a37a74-b5c7-11f0-93cd-00090ffe0001', 'MAGANG', NULL, 1, '2025-10-30 19:34:12', '2025-10-30 19:34:12', 0),
	('6b4497e1-b5c7-11f0-93cd-00090ffe0001', 'PEKERJA HARIAN LEPAS', NULL, 1, '2025-10-30 19:34:15', '2025-10-30 19:34:15', 0);

-- Dumping data for table ldr.rf_order_status: ~9 rows (approximately)
INSERT INTO `rf_order_status` (`id`, `code`, `name`, `description`, `color_code`, `is_active`, `sequence_order`, `created_at`, `updated_at`, `version`) VALUES
	('2e79b5bd-b529-11f0-8db2-00090ffe0001', 'DRAFT', 'Draft', 'Order dalam tahap persiapan', '#6B7280', 1, 1, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79c06d-b529-11f0-8db2-00090ffe0001', 'SUBMITTED', 'Submitted', 'Order telah disubmit untuk review', '#3B82F6', 1, 2, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79cab8-b529-11f0-8db2-00090ffe0001', 'REVIEW', 'Under Review', 'Order sedang dalam proses review', '#F59E0B', 1, 3, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79cfa2-b529-11f0-8db2-00090ffe0001', 'APPROVED', 'Approved', 'Order telah disetujui', '#10B981', 1, 4, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79d319-b529-11f0-8db2-00090ffe0001', 'REJECTED', 'Rejected', 'Order ditolak', '#EF4444', 1, 5, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79d624-b529-11f0-8db2-00090ffe0001', 'IN_PROGRESS', 'In Progress', 'Order sedang diproses', '#8B5CF6', 1, 6, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79d8fb-b529-11f0-8db2-00090ffe0001', 'ON_HOLD', 'On Hold', 'Order ditunda sementara', '#F59E0B', 1, 7, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79dbc5-b529-11f0-8db2-00090ffe0001', 'COMPLETED', 'Completed', 'Order telah selesai', '#10B981', 1, 8, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e79dfc7-b529-11f0-8db2-00090ffe0001', 'CANCELLED', 'Cancelled', 'Order dibatalkan', '#EF4444', 1, 9, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0);

-- Dumping data for table ldr.rf_priority: ~4 rows (approximately)
INSERT INTO `rf_priority` (`id`, `code`, `name`, `description`, `color_code`, `default_deadline_days`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('2e7abab6-b529-11f0-8db2-00090ffe0001', 'LOW', 'Low', 'Prioritas rendah', '#6B7280', 14, 1, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e7ac03c-b529-11f0-8db2-00090ffe0001', 'NORMAL', 'Normal', 'Prioritas normal', '#3B82F6', 7, 1, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e7ac389-b529-11f0-8db2-00090ffe0001', 'HIGH', 'High', 'Prioritas tinggi', '#F59E0B', 5, 1, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0),
	('2e7ac692-b529-11f0-8db2-00090ffe0001', 'URGENT', 'Urgent', 'Prioritas mendesak', '#EF4444', 2, 1, '2025-10-30 00:41:32', '2025-10-30 00:41:32', 0);

-- Dumping data for table ldr.rf_service_cost_type: ~2 rows (approximately)
INSERT INTO `rf_service_cost_type` (`id`, `name`, `description`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('2e995980-b5c7-11f0-93cd-00090ffe0001', 'Total Biaya Jasa', NULL, 1, '2025-10-30 19:32:33', '2025-10-30 19:32:33', 0),
	('3c5c9795-b5c7-11f0-93cd-00090ffe0001', 'Management Fee', NULL, 1, '2025-10-30 19:32:56', '2025-10-30 19:32:56', 0);

-- Dumping data for table ldr.users: ~0 rows (approximately)
INSERT INTO `users` (`id`, `username`, `password`, `email`, `full_name`, `role`, `department`, `position`, `phone`, `is_active`, `is_deleted`, `deleted_at`, `last_login`, `failed_login_attempts`, `locked_until`, `created_at`, `updated_at`, `version`) VALUES
	('f54c1a38-4a90-4241-9ffd-ba15c99f0dcb', 'legal', '$2a$10$b.0S5VqFqBfGGSQmqigxGuxWSzasJSLiDIxLtRDMk20qAHjrcpxR.', 'user@example.com', 'user legal', 'USER', 'LEGAL', 'LEGAL', '+1234567890', 1, 0, NULL, '2025-10-30 18:43:43', 0, NULL, NOW(), NOW(), 8);

-- Dumping data for table ldr.workflow: ~0 rows (approximately)
INSERT INTO `workflow` (`id`, `nama`, `deskripsi`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('98478187-b5c8-11f0-93cd-00090ffe0001', 'LDR', NULL, 1, '2025-10-30 19:42:40', '2025-10-30 19:42:40', 0);

-- Dumping data for table ldr.workflow_detail: ~2 rows (approximately)
INSERT INTO `workflow_detail` (`id`, `workflow_id`, `current_stage`, `next_stage`, `return_stage`, `reject_stage`, `sla`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
	('f98d92ed-b5c8-11f0-93cd-00090ffe0001', '98478187-b5c8-11f0-93cd-00090ffe0001', 'legal', 'bd', 'legal', 'legal', 2, 1, '2025-10-30 19:45:23', '2025-11-01 03:18:57', 0),
	('fa707444-b5c8-11f0-93cd-00090ffe0001', '98478187-b5c8-11f0-93cd-00090ffe0001', 'bd', 'legal', 'bd', 'bd', 5, 1, '2025-10-30 19:45:24', '2025-11-01 03:18:58', 0);
