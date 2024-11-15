import socket
import threading
import subprocess
import ports
import os
# ME DAN IP. COMO SEGUNDO PARAMETRO QUIET O VERBOSE, CON QUIET DEVUELVE COSAS CON VERBOSE MAS COSAS.
# PUERTOS ABIERTOS TCP, VERSION (80 ES HTTP), TECNOLOGIA, SISTEMA OPERATIVO.



class Scanner:
    def __init__(self, ip, verbose=False):

        
        self.ip = ip
        self.verbose = verbose
        self.open_ports = []
        self.closed_ports = []

    def scan_port(self, port):
        try:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.settimeout(0.1)
            
            s.connect((self.ip, port))   
            
            s.close()

            self.open_ports.append(port)
        except:
            self.closed_ports.append(port)

    def scan_ports(self):

        threads = []

        for port in ports.PORT_SERVICE_MAP:

            t = threading.Thread(target=self.scan_port, args=(port,))
            threads.append(t)
            t.start()


        for t in threads:
            t.join()

    def getOperatingSystem(self):

        command = "ping -c 1 " + self.ip;
        result = subprocess.check_output(command, shell=True, text=True)
        ttlNumber = result.split("ttl=")[1].split(" ")[0]

        if ttlNumber <= 70:
            return "Linux"
        else:
            return "Windows"
        
    def getNameOnTheNetwork(self):
        # Windows

        if self.getOperatingSystem() == "Windows":
            command = "nbtstat -A " + self.ip;
            result = subprocess.check_output(command, shell=True, text=True)
            return result.split(" ")[0]
        else:
            command = "nmblookup -A " + self.ip;
            result = subprocess.check_output(command, shell=True, text=True)
            return result.split(" ")[0]
        

    def scan(self):
        self.scan_ports()
        if self.verbose:
            print(f"Open ports: {self.open_ports}")
            print(f"Closed ports: {self.closed_ports}")
        else:
            print(f"Open ports: {self.open_ports}")

    def get_open_ports(self):
        return self.open_ports

    def get_closed_ports(self):
        return self.closed_ports

    def get_ip(self):
        return self.ip

    def get_verbose(self):
        return self.verbose

    def set_ip(self, ip):
        self.ip = ip

    def set_verbose(self, verbose):
        self.verbose = verbose


def main():
    ip = "192.168.1.151"
    scanner = Scanner(ip, verbose=False)
    scanner.scan()

    print(scanner.get_open_ports())
   
 


main()
     