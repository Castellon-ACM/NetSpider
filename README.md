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

- **Java:** ☕ Main program for control the scanning process, managing threads, and generating PDF reports.  

- **C++:** 🛠️ High-speed module for port scanning and service detection.
  copyOnWriteArrayList: 📋 A thread-safe variant of ArrayList in Java that allows safe concurrent access and modification. It ensures that multiple threads can read from and modify the list without encountering concurrency issues, making it ideal for multithreaded environments.

- **Equilibrator Class:** 🏗️ The Equilibrator class utilizes CopyOnWriteArrayList to manage concurrent access to various queues containing Node objects and ProcessBuilder instances. This ensures thread safety when multiple threads are processing nodes and managing processes simultaneously.

**Functionality of CopyOnWriteArrayList in the Equilibrator Class:**
The Equilibrator class uses CopyOnWriteArrayList to handle several queues that are accessed and modified by multiple threads concurrently. This ensures that operations on the lists do not interfere with each other, even when threads are adding or removing elements simultaneously.

Key Methods in Equilibrator Using CopyOnWriteArrayList:

- **clearAndPrepareQueue():**

Description: This method clears the processQueue and adds new processes (instances of ProcessBuilder) to cProcesses. It uses CopyOnWriteArrayList to ensure that adding to cProcesses is done safely, without interference from other threads.  

- **clearAndExport():**

Description: This method exports processed nodes from ProcessedQueue into an ArrayList and then clears the ProcessedQueue. By using CopyOnWriteArrayList, it ensures safe access and modification of ProcessedQueue, even when other threads are working with the list.

- **importNodes():**

Description: This method imports nodes into the processQueue. Thanks to CopyOnWriteArrayList, concurrent access to the list is safe, allowing nodes to be added without worrying about race conditions or interference from other threads.

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" alt="Java Logo" width="50"/> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cplusplus/cplusplus-original.svg" alt="C++ Logo" width="50"/>
</div>

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
