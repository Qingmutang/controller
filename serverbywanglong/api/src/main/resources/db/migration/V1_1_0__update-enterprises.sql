UPDATE
	enterprises
SET certificate_type = 'SHNAGHAI_EPAC'
WHERE
	certificate_type = 'SHNAGHAI_ELECTRIC_POWER_ASSOCIATION_CERTIFICATION';

REPLACE INTO `certificate_types` (`id`, `is_active`, `code`, `name`, `url`) VALUES
	(1, b'1', 'MODIANCERTIFICATION', '魔电认证', NULL),
	(2, b'1', 'SHNAGHAI_EPAC', '上海电力协会', 'image/shdlxh.png'),
	(3, b'1', 'NONE', '未认证', NULL),
	(4, b'1', 'BEIJING_EPAC', '北京电力协会', 'image/logo/bj.jpg'),
	(5, b'1', 'LIAONING_EPAC', '辽宁电力协会', 'image/logo/ln.jpg'),
	(6, b'1', 'HEILONGJIANG_EPAC', '黑龙江电力协会', 'image/logo/hlj.jpg'),
	(7, b'1', 'JIANGSU_EPAC', '江苏电力协会', 'image/logo/js.jpg'),
	(8, b'1', 'SHANDONG_EPAC', '山东电力协会', 'image/logo/sd.jpg'),
	(9, b'1', 'ANHUI_EPAC', '安徽电力协会', 'image/logo/ah.jpg');


update enterprises a, enterprises b set a.found_date=b.issue_date ,a.issue_date=b.found_date
where a.id=b.id;