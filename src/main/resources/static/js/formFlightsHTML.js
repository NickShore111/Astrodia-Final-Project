export const roundtripInputHTML = `
    <form th:action="@{/astrodia/flights/roundtrip}" class="d-flex-inline" method="GET">
    <div class="row g-2 justify-content-center" id="search-inputs">
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
                    class="form-control form-select-lg mb-3 col"
                    type="text"
                    placeholder="Departure date"
                    id="departDate"
                  />
                </div>
                <div class="col-5 mx-1">
                  <input
                    class="form-control form-select-lg mb-3 col"
                    type="text"
                    id="returnDate"
                    placeholder="Return date"
                  />
                </div>
              </div>
          </div>
    </div>
</form>
`;

export const onewayInputHTML = `
  <form th:action="@{/astrodia/flights/oneway}" class="d-flex-inline" method="GET">
    <div class="row g-2 justify-content-center" id="search-inputs">
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
          <div class="col-8 mx-1">
            <input
              class="form-control form-select-lg mb-3 col"
              type="text"
              placeholder="Departure date"
              id="departDate"
            />
          </div>
        </div>
    </div>
</form>
`;

export const multiportInputHTML = `
<form th:action="@{/astrodia/flights/multiport}" class="d-flex-inline" method="GET">
<div class="row g-2 justify-content-center my-1" id="search-inputs">
    <div class="row p-0 multi-port-shuttle-count">
      <div class="col offset-1"><h6>Shuttle 1</h6></div>
    </div>
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
      <div class="col-8 mx-1">
        <input
          class="form-control form-select-lg mb-3 col datepicker"
          type="text"
          placeholder="Departure date"
          id="fs-departDate1"
        />
      </div>
    </div>
  </div>

  <div class="row g-2 justify-content-center my-1" id="search-inputs">
    <div class="row p-0 multi-port-shuttle-count">
      <div class="col offset-1"><h6>Shuttle 2</h6></div>
    </div>
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
      <div class="col-8 mx-1">
        <input
          class="form-control form-select-lg mb-3 col datepicker"
          type="text"
          placeholder="Departure date"
          id="fs-departDate2"
        />
      </div>
    </div>
  </div>
  <div class="row">
    <div class="container d-flex">

      <svg class="uitk-icon-medium mb-1 offset-9"
      aria-hidden="true" viewBox="0 0 24 24" fill="#0d6efd" id="add_flight"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink">
        <svg>
          <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"></path>
        </svg>
      </svg>
      <p class="fw-bold text-primary" id="add_flight">Add another flight</p>

    </div>

  </div>
 </form>
`;
