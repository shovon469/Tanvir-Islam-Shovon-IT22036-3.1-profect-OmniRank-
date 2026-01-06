# OmniRank: One Handle To Rule Them All 🏆

![OmniRank Banner](https://via.placeholder.com/1200x400?text=OmniRank+Dashboard+Preview)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**OmniRank** is a centralized competitive programming tracker and aggregator. It unifies user statistics from major coding platforms—**Codeforces, LeetCode, AtCoder, CodeChef, and CSES**—into a single, holistic dashboard. By leveraging public APIs and secure web scraping, OmniRank provides real-time analytics and AI-driven problem recommendations to help programmers streamline their growth.

---

## 📑 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [System Architecture](#-system-architecture)
- [Screenshots](#-screenshots)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Future Roadmap](#-future-roadmap)
- [Team & Acknowledgements](#-team--acknowledgements)

---

## 🚀 Features

* **Unified Dashboard:** View total problems solved across 5+ platforms in one place.
* **Hybrid Data Fetching:**
    * **REST/GraphQL APIs:** For Codeforces and LeetCode.
    * **Web Scraping (Jsoup):** For platforms without public APIs (CSES, CodeChef).
* **AI Recommendation Engine:** A heuristic algorithm that analyzes your recent rating and suggests the optimal "next problem" to solve (Progressive Overload).
* **Activity Streak:** Automated tracking of active coding days to maintain consistency.
* **Glassmorphism UI:** A modern, responsive interface built with Tailwind CSS.
* **Detailed Analytics:** Filter solved problems by tags, difficulty, and date.

---

## 🛠 Tech Stack

| Component | Technology |
| :--- | :--- |
| **Backend** | Java 17 (LTS), Spring Boot 3.4.1 |
| **Database** | MySQL 8.0, Hibernate (JPA) |
| **Frontend** | Thymeleaf (SSR), Tailwind CSS, Chart.js |
| **Tools** | Maven, Lombok, Jsoup (Scraping), Git |
| **IDE** | IntelliJ IDEA Ultimate |

---

## 🏗 System Architecture

OmniRank follows the **Model-View-Controller (MVC)** architectural pattern:

1.  **Controller Layer:** Handles HTTP requests and manages session logic.
2.  **Service Layer (Aggregator):** Connects to external platforms via `RestTemplate` (APIs) and `Jsoup` (HTML Parsing). It normalizes disparate data (Ratings vs. Tiers) into a unified format.
3.  **Repository Layer:** Persists user profiles and cached problem history in MySQL.

---

## 📸 Screenshots

| **Unified Dashboard** | **Detailed Analytics** |
|:---:|:---:|
| ![Dashboard]() | ![Analytics](path/to/analytics-image.png) |

| **AI Recommendation** | **Dark Mode Login** |
|:---:|:---:|
| ![AI Logic](path/to/ai-image.png) | ![Login](path/to/login-image.png) |

---

## ⚡ Getting Started

Follow these steps to set up the project locally.

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* MySQL Server 8.0+
* Maven

### Installation

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/your-username/omnirank.git](https://github.com/your-username/omnirank.git)
    cd omnirank
    ```

2.  **Create the Database**
    Open MySQL Workbench or CLI and run:
    ```sql
    CREATE DATABASE omnirank;
    ```

3.  **Configure Application Properties**
    Open `src/main/resources/application.properties` and update your credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/omnirank
    spring.datasource.username=root
    spring.datasource.password=YOUR_PASSWORD
    spring.jpa.hibernate.ddl-auto=update
    ```

4.  **Build and Run**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

5.  **Access the App**
    Open your browser and navigate to: `http://localhost:8080`

---

## 🔮 Future Roadmap

- [ ] **Mobile Application:** Native app development using Flutter.
- [ ] **Contest Calendar:** Google Calendar integration for upcoming Codeforces rounds.
- [ ] **Machine Learning:** Replace heuristic AI with Collaborative Filtering for better recommendations.
- [ ] **Social Leaderboards:** Create custom groups to compete with university peers.

---

## 👥 Team & Acknowledgements

**Project Team (MBSTU - ICT Dept):**
* **Rahat Bin Hafiz** (ID: IT22034) - *Lead Developer & Backend*
   * 📞 Contact: (+880) 1521-700491
   * 📧 Email: rahatbinhafiz40@gmail.com
* **Shourav Mallik** (ID: IT22032) - *Frontend & UI/UX*
* **Md. Tanvir Islam Shovon** (ID: IT22036) - *Database & Documentation*

**Supervised By:**
* **Dr. Ziaur Rahman**
    * Professor, Department of ICT
    * Mawlana Bhashani Science & Technology University (MBSTU)
