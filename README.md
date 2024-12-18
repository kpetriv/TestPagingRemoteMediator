
# Presentation:

## General

I haven't worked much with paged loading much before, as most of our screens are one shot loading operations
and the screen does does having a paginated list doesn't actually save the data. So I had to make a choice
of what I am going to use. The Paging library was the one I am familiar at makes this loading simple and
compose is the UI I have been working predominantly over the last 2 years, so I picked those. 

Although the app is functional and it loads the items in a paginated way directly into the database, which 
in turns supplies the PagingData to the UI, there are UI issues when the new data is loaded into the database
and the PagingData gets invalidated and is refreshed. The lazy list changes the position for some reason
and I didn't manage to fix that. Also, working with the compose version of the PagingData as a flow
which exposes the lading state wasn't the norm for me and it felt out of place.

## Context

Curious, challenged, worried about the time constraint. It was an eventful weekend without the practical test,
so adding this to the weekend was quite a challenge in itself.

## Architecture

I wanted to split the app into actual modules and started doing it, but then decided to simplify it, since 
there was no specific requirement to have it this way. Nevertheless I separated everything into neat packages
to be able to modularise properly and abstract the implementations so they can be internal to the respective modules.

The App module with the UI would house the Injection and coordinate Network, Repositories, Database, Usecases.
The interfaces, transformers and models would be in the Common so it could be a non Android module in the future.

### Patterns and Good practices
- Factory pattern : created viewmodels easily in one place
- Avoided using the Singleton anti-pattern (Injection has instances of all the needed classes)
- Dependency Injection
- Lazy init
- Single Source of Truth as the database
- Dependency Inversion
- DRY : abstracted the way VM's and services are created
- Clean architecture


## Development Strategy

The idea was to have a clean architecture app with small incremental commits, but it went a bit 
wrong when I realised I was wasting too much time on non-necessary things which are out of scope.
Although it would have been nice having a beautiful app with nice gradle setups, I decided to 
follow the YAGNI principle and ditch the complexity in favor of functionality. 
So the commit and development strategy was compromised because at that point I was more
anxious about the time constraint.

## Code comments

In general I think it's a relatively bad piece of work because it lacks:
- Network adapters to encapsulate the state of the service call
- Error handling and logging
- Scalable UI and lack of the navigation concept
- No UI state/event handling
- Unit/Integration/UI testing

It does have a decent structure that allows minimal change to the layers when one layer implementation 
detail is changed.
