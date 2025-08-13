CREATE TABLE user_news_preference(
   id SERIAL PRIMARY KEY,
   lang  VARCHAR(255) NOT NULL,
   user_id INT NOT NULL UNIQUE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (user_id)  REFERENCES users(id)
);

CREATE TABLE news_categories(
  id SERIAL PRIMARY KEY,
  display_name VARCHAR(255) NOT NULL,
  val  VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE news_countries(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  val VARCHAR(10) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_preference_news_categories(
   user_news_preference_id INT NOT NULL,
   category_id INT NOT NULL,
   PRIMARY KEY (user_news_preference_id, category_id),
   FOREIGN KEY (user_news_preference_id)  REFERENCES user_news_preference(id) ON DELETE CASCADE,
   FOREIGN KEY (category_id)  REFERENCES news_categories(id) ON DELETE CASCADE
);

CREATE TABLE user_preference_news_countries(
   user_news_preference_id INT NOT NULL,
   country_id INT NOT NULL,
   PRIMARY KEY (user_news_preference_id, country_id),
   FOREIGN KEY (user_news_preference_id)  REFERENCES user_news_preference(id) ON DELETE CASCADE,
   FOREIGN KEY (country_id)  REFERENCES news_countries(id) ON DELETE CASCADE
);