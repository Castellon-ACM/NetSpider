# 🕸️ NetSpider 🕸️

NetSpider is a high-performance network scanner that continuously monitors the network for new IPs. Designed with a hybrid architecture, it combines the power of **Java** ☕ and **C++** 🛠️ for optimal efficiency and scalability.

---

## ✨ Features

- **🌐 Continuous Network Scanning:**  
  Detects new IPs in real-time and dynamically processes them.

- **🚀 Optimized C++ Module:**  
  - Scans ports and identifies services with maximum efficiency.  
  - Designed to minimize scanning time.

- **📄 PDF Report Generation:**  
  - All results are processed in the Java main program and exported as a detailed PDF report.

- **⚙️ Dynamic CPU Load Management:**  
  - The Java program dynamically spawns the C++ scanning module based on CPU usage.  
  - When CPU usage reaches a specific threshold, process instantiation is paused until the load decreases, ensuring system stability.  

- **🧵 Thread Optimization:**  
  - Java threads are optimized to avoid busy waiting, significantly reducing CPU load during idle times.

---

## 🛠️ Technology Stack

- **Java:** ☕ Main program for orchestrating the scanning process, managing threads, and generating PDF reports.  
![Java](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg)

- **C++:** 🛠️ High-speed module for port scanning and service detection.  
![C++](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cplusplus/cplusplus-original.svg)

---

## 🌟 Use Cases

- **🔍 Network Monitoring:** Ideal for IT administrators who need to keep track of active devices and services.  
- **🔐 Security Assessments:** Quickly identifies open ports and running services for analysis.  
- **📝 Report Generation:** Automatically generates structured reports in PDF format.

---

## 🤝 Authors

Manuel Cervantes - Felix Caba - Alejandro Castellón

---

## 📜 License

This project is licensed under the [BLOSTE](LICENSE).

---

Enjoy using **NetSpider** and keep your network under control! 🎉
