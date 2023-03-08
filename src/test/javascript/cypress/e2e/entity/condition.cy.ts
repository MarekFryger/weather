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

describe('Condition e2e test', () => {
  const conditionPageUrl = '/condition';
  const conditionPageUrlPattern = new RegExp('/condition(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const conditionSample = {};

  let condition;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/conditions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/conditions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/conditions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (condition) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/conditions/${condition.id}`,
      }).then(() => {
        condition = undefined;
      });
    }
  });

  it('Conditions menu should load Conditions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('condition');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Condition').should('exist');
    cy.url().should('match', conditionPageUrlPattern);
  });

  describe('Condition page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(conditionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Condition page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/condition/new$'));
        cy.getEntityCreateUpdateHeading('Condition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', conditionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/conditions',
          body: conditionSample,
        }).then(({ body }) => {
          condition = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/conditions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [condition],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(conditionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Condition page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('condition');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', conditionPageUrlPattern);
      });

      it('edit button click should load edit Condition page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Condition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', conditionPageUrlPattern);
      });

      it('edit button click should load edit Condition page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Condition');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', conditionPageUrlPattern);
      });

      it('last delete button click should delete instance of Condition', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('condition').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', conditionPageUrlPattern);

        condition = undefined;
      });
    });
  });

  describe('new Condition page', () => {
    beforeEach(() => {
      cy.visit(`${conditionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Condition');
    });

    it('should create an instance of Condition', () => {
      cy.get(`[data-cy="description"]`).type('Lebanon Valley Sleek').should('have.value', 'Lebanon Valley Sleek');

      cy.get(`[data-cy="iconLink"]`).type('policy Barthelemy').should('have.value', 'policy Barthelemy');

      cy.get(`[data-cy="code"]`).type('41534').should('have.value', '41534');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        condition = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', conditionPageUrlPattern);
    });
  });
});
