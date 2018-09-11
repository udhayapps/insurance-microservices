package main

import (
	"strings"
	"net/http"
	"encoding/json"
	"github.com/gorilla/mux"
	"log"
)

type Errors struct {
	ErrorMessage string `json:"error_message,omitempty"`
}

type Health struct {
	Status string `json:"status,omitempty"`
}

type Policy struct {
	PolicyReference string `json:"policyReference,omitempty"`
}

func GetHealthEndPoint(w http.ResponseWriter, r *http.Request) {
	log.Print("INFO: /health called...")
	w.Header().Set("Content-Type", "application/json")
	health := Health{"UP"}
	json.NewEncoder(w).Encode(health)
}

func GetConvertEndpoint(w http.ResponseWriter, r *http.Request) {
	log.Print("INFO: /convert called..")
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	quoteReference := params["quote-reference"]
	if strings.Contains(quoteReference, "QTE") {
		policy := Policy{"POL" + "-" + strings.Split(quoteReference, "-")[1]}
		json.NewEncoder(w).Encode(policy)
	} else {
		log.Print("ERROR: /convert ERROR")
		errorMessage := Errors{"ERROR: Failed to Convert Quote to Policy"}
		json.NewEncoder(w).Encode(errorMessage)
	}
}

func main() {
	router := mux.NewRouter()
	router.HandleFunc("/convert/{quote-reference}", GetConvertEndpoint).Methods("GET")
	router.HandleFunc("/health", GetHealthEndPoint).Methods("GET")
	log.Fatal(http.ListenAndServe(":9999", router))
}
