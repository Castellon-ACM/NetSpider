import re
import socket
import threading
import subprocess
import ports
import platform
import json
# ME DAN IP. COMO SEGUNDO PARAMETRO QUIET O VERBOSE, CON QUIET DEVUELVE COSAS CON VERBOSE MAS COSAS.
# PUERTOS ABIERTOS TCP, VERSION (80 ES HTTP), TECNOLOGIA, SISTEMA OPERATIVO.

class Scanner:
    def __init__(self, ip, verbose=False):

        
        self.ip = ip
        self.verbose = verbose
        self.open_ports = {}
        self.closed_ports = 0
        

        self.operating_system = self.getOperatingSystem()


    def scan_port(self, port):
        try:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.settimeout(0.1)
            
            s.connect((self.ip, port))   
            
            s.close()

        

            self.open_ports[port] = ports.PORT_SERVICE_MAP[port]
            

        except:

            self.closed_ports += 1
            

            

    def scan_ports(self):

        threads = []
        for port in ports.PORT_SERVICE_MAP:


            t = threading.Thread(target=self.scan_port, args=(port,))
            threads.append(t)
            t.start()


        for t in threads:
            t.join()

    def getOperatingSystem(self):
        ttlNumber = 0

        if platform.system().lower() == "windows":
            command = f"ping -n 1 {self.ip}"
        elif platform.system().lower() == "darwin":
            command = f"ping -c 1 {self.ip}"
        else:
            command = f"ping -c 1 {self.ip}"
    
        try:
         
            result = subprocess.check_output(command, shell=True, text=True)
        
           
            ttl_match = re.search(r"TTL[=:]\s*(\d+)", result, re.IGNORECASE)
        
            if ttl_match:
                ttlNumber = int(ttl_match.group(1))
                print(f"TTL Number: {ttlNumber}")
            
                if ttlNumber <= 70:
                    return "Linux"
                else:
                    return "Windows"
                
            return "Undetermined"
        
        except subprocess.CalledProcessError:
            return "NotReachable"
        except Exception as e:
            return f"Error: {str(e)}"

        

    def scan(self):
        self.scan_ports()
        


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

    ip = "192.168.151.1"
    scanner = Scanner(ip, verbose=True)
    scanner.scan()

    scannerJSON = json.dumps(scanner.__dict__, indent=4)

    print(scannerJSON)


   
main()
     