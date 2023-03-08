import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Current e2e test', () => {
  const currentPageUrl = '/current';
  const currentPageUrlPattern = new RegExp('/current(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const currentSample = {};

  let current;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/currents+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/currents').as('postEntityRequest');
    cy.intercept('DELETE', '/api/currents/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (current) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/currents/${current.id}`,
      }).then(() => {
        current = undefined;
      });
    }
  });

  it('Currents menu should load Currents page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('current');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Current').should('exist');
    cy.url().should('match', currentPageUrlPattern);
  });

  describe('Current page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(currentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Current page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/current/new$'));
        cy.getEntityCreateUpdateHeading('Current');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/currents',
          body: currentSample,
        }).then(({ body }) => {
          current = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/currents+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [current],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(currentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Current page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('current');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentPageUrlPattern);
      });

      it('edit button click should load edit Current page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Current');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentPageUrlPattern);
      });

      it('edit button click should load edit Current page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Current');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentPageUrlPattern);
      });

      it('last delete button click should delete instance of Current', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('current').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentPageUrlPattern);

        current = undefined;
      });
    });
  });

  describe('new Current page', () => {
    beforeEach(() => {
      cy.visit(`${currentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Current');
    });

    it('should create an instance of Current', () => {
      cy.get(`[data-cy="lastUpdateLong"]`).type('53839').should('have.value', '53839');

      cy.get(`[data-cy="lastUpdateTime"]`).type('FTP Sports').should('have.value', 'FTP Sports');

      cy.get(`[data-cy="tempC"]`).type('4554').should('have.value', '4554');

      cy.get(`[data-cy="tempF"]`).type('74424').should('have.value', '74424');

      cy.get(`[data-cy="isDay"]`).should('not.be.checked');
      cy.get(`[data-cy="isDay"]`).click().should('be.checked');

      cy.get(`[data-cy="windMph"]`).type('96111').should('have.value', '96111');

      cy.get(`[data-cy="windKph"]`).type('59645').should('have.value', '59645');

      cy.get(`[data-cy="windDegree"]`).type('51422').should('have.value', '51422');

      cy.get(`[data-cy="windDirection"]`).type('indigo').should('have.value', 'indigo');

      cy.get(`[data-cy="pressureMb"]`).type('20801').should('have.value', '20801');

      cy.get(`[data-cy="pressureIn"]`).type('64789').should('have.value', '64789');

      cy.get(`[data-cy="precipMm"]`).type('68264').should('have.value', '68264');

      cy.get(`[data-cy="precipIn"]`).type('69609').should('have.value', '69609');

      cy.get(`[data-cy="humidity"]`).type('48329').should('have.value', '48329');

      cy.get(`[data-cy="cloud"]`).type('5287').should('have.value', '5287');

      cy.get(`[data-cy="feelslikeC"]`).type('1511').should('have.value', '1511');

      cy.get(`[data-cy="feelslikeF"]`).type('50743').should('have.value', '50743');

      cy.get(`[data-cy="uv"]`).type('32636').should('have.value', '32636');

      cy.get(`[data-cy="gustMph"]`).type('73989').should('have.value', '73989');

      cy.get(`[data-cy="gustKph"]`).type('18619').should('have.value', '18619');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        current = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', currentPageUrlPattern);
    });
  });
});
