update users set password = if(name != 'admin', MD5(password), password);