export const roundtripInputHTML = `
    <form id="roundtripForm" th:action="@{/astrodia/flights/roundtrip}" class="d-flex-inline" method="GET">
    <div class="row g-2 justify-content-center" id="roundtrip-search-inputs">
          <div class="col-lg-3">
                <div class="form-floating">
                  <select class="form-select" id="departurePort">
                    <option selected>Select port</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                  </select>
                  <label for="floatingSelect">Departing from</label>
                </div>
              </div>
              <div class="col-lg-3">
                <div class="form-floating">
                  <select class="form-select" id="arrivalPort">
                    <option selected>Select port</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                  </select>
                  <label for="floatingSelect">Going to</label>
                </div>
              </div>
              <div class="col-lg-4 d-flex justify-content-center" id="date-input-container">
                <div class="col-5 mx-1">
                  <input
                    class="form-control form-select-lg mb-3 col datepicker"
                    type="text"
                    id="departureDate"
                    placeholder="Departure date"
                  />
                </div>
                <div class="col-5 mx-1">
                  <input
                    class="form-control form-select-lg mb-3 col datepicker"
                    type="text"
                    id="arrivalDate"
                    placeholder="Return date"
                  />
                </div>
              </div>
          </div>
    </div>
</form>`;

export const onewayInputHTML = `
  <form id="onewayForm" th:action="@{/astrodia/flights/oneway}" class="d-flex-inline" method="GET">
    <div class="row g-2 justify-content-center" id="oneway-search-inputs">
        <div class="col-lg-3">
          <div class="form-floating">
            <select class="form-select" id="departurePort_1">
              <option selected>Select port</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
            </select>
            <label for="floatingSelect">Departing from</label>
          </div>
        </div>
        <div class="col-lg-3">
          <div class="form-floating">
            <select class="form-select" id="arrivalPort_1">
              <option selected>Select port</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
            </select>
            <label for="floatingSelect">Going to</label>
          </div>
        </div>
        <div class="col-lg-4 d-flex justify-content-center" id="date-input-container">
          <div class="col-8 mx-1">
            <input
              class="form-control form-select-lg mb-3 col departureDate"
              type="text"
              placeholder="Departure date"
              id="departureDate_1"
            />
          </div>
        </div>
    </div>
</form>`;

export const multiportInputHTML = `
<form id="multiportForm" th:action="@{/astrodia/flights/multiport}" class="d-flex-inline" method="GET">
<div class="row g-2 justify-content-center my-1 search-inputs" id="multiport-shuttle1">
    <div class="row p-0">
      <div class="col offset-1" id="shuttle-number"><h6>Shuttle 1</h6></div>
    </div>
    <div class="col-lg-3">
      <div class="form-floating">
        <select class="form-select" id="departurePort_1"
        name="departurePort_1">
          <option value="" selected>Select port</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <label for="floatingSelect">Departing from</label>
      </div>
    </div>
    <div class="col-lg-3">
      <div class="form-floating">
        <select class="form-select" id="arrivalPort_1"
        name="arrivalPort_1">
          <option value="" selected>Select port</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <label for="floatingSelect">Going to</label>
      </div>
    </div>
    <div class="col-lg-4 d-flex justify-content-center" id="date-input-container">
      <div class="col-8 mx-1">
        <input
          class="form-control form-select-lg mb-3 col datepicker"
          type="text"
          placeholder="Departure date"
          id="departureDate_1"
        />
      </div>
    </div>
  </div>
  <div class="row g-2 justify-content-center my-1 search-inputs" id="multiport-shuttle2">
    <div class="row p-0 multi-port-shuttle-count">
      <div class="col offset-1"><h6>Shuttle 2</h6></div>
    </div>
    <div class="col-lg-3">
      <div class="form-floating">
        <select class="form-select" id="departurePort_2"
        name="departurePort_2">
          <option value="" selected>Select port</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <label for="floatingSelect">Departing from</label>
      </div>
    </div>
    <div class="col-lg-3">
      <div class="form-floating">
        <select class="form-select" id="arrivalPort_2"
        name="arrivalPort_2">
          <option value="" selected>Select port</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <label for="floatingSelect">Going to</label>
      </div>
    </div>
    <div class="col-lg-4 d-flex justify-content-center" id="date-input-container">
      <div class="col-8 mx-1">
        <input
          class="form-control form-select-lg mb-3 col datepicker"
          type="text"
          placeholder="Departure date"
          id="departureDate_2"
        />
      </div>
    </div>
  </div>
  <div class="row justify-content-end" id="add-flight-btn-row">
  <div class="col-9"></div>
    <div class="col text-nowrap">
        <span class="p-2" id="add-flight-btn">
          <i class="fa-solid fa-plus"></i>
          <p style="display:inline;" class="fw-bold text-primary">Add another flight</p>
        </span>
    </div>
  </div>
 </form>`;

export const addFlightToMultiportHTML = `
    <div class="col-lg-3">
      <div class="form-floating">
        <select class="form-select" id="newDeparturePort"
        name="newDeparturePort">
          <option value="" selected>Select port</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <label for="floatingSelect">Departing from</label>
      </div>
    </div>
    <div class="col-lg-3">
      <div class="form-floating">
        <select class="form-select" id="newArrivalPort"
        name="newArrivalPort">
          <option value="" selected>Select port</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <label for="floatingSelect">Going to</label>
      </div>
    </div>
    <div class="col-lg-4 d-flex justify-content-center" id="date-input-container">
      <div class="col-8 mx-1">
        <input
          class="form-control form-select-lg mb-3 col datepicker"
          type="text"
          placeholder="Departure date"
          id="newDepartureDatepicker"
        />
      </div>
    </div>`;